package com.dazhi.libroot.root;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.dazhi.libroot.R;
import com.dazhi.libroot.inte.InteRootEngineLifecycle;
import com.dazhi.libroot.inte.InteRootView;
import com.dazhi.libroot.ui.dialog.DialogLoad;
import com.dazhi.libroot.util.UtConfig;
import com.dazhi.libroot.util.UtRoot;
import com.dazhi.libroot.util.UtStack;
import com.dazhi.libroot.util.UtStatusBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 功能：根Activity
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2019/2/26 17:28
 * 修改日期：2019/2/26 17:28
 */
@RuntimePermissions //标记需要运行时判断的类(用于动态生成代理类)
public abstract class RootActivity extends AppCompatActivity implements InteRootView {
    private DialogLoad dialogLoading;  //进度对话框
    private AlertDialog dialogMsgBox; //警告对话框
    // 依赖注入：生命周期引擎
    @SuppressWarnings("unused")
    private InteRootEngineLifecycle inteRootEngineLifecycle = UtConfig.self().getEngineLifecycle();

    /*==============抽象方法============*/
    /**/
    protected void initAtSetContentViewBefore(){}
    /*获得布局id*/
    protected abstract int getLayoutId();
    protected View getLayoutView(){
        return null;
    }
    /*初始化配置等，如标题*/
    protected abstract void initConfig(TextView tvToolTitle);
    /*初始化视图等*/
    protected abstract void initViewAndDataAndEvent();


    // 设置为true为了解决部分华为手机按Home后，点应用图标，应用重启问题
    @Override
    public boolean moveTaskToBack(boolean nonRoot) {
        return super.moveTaskToBack(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        if(inteRootEngineLifecycle!=null) {
            inteRootEngineLifecycle.onCreateRoot(this);
        }
        //
        initAtSetContentViewBefore();
        int layoutId = getLayoutId();
        // 当要设置view，而不是资源ID时，需实现getLayoutId回0，
        // 并覆盖getLayoutView方法，回实际的view
        if(layoutId==0){
            setContentView(getLayoutView());
        }else {
            setContentView(layoutId);
        }
        //
        TextView tvContent = findViewById(R.id.librootToolbarTitle);
        if(tvContent!=null){
            tvContent.setTextColor(UtStatusBar.getToolbarCtColor());
        }
        //状态条配置
        UtStatusBar.setStatusBarColor(this, UtStatusBar.getToolbarBgColor());
        //
        Toolbar toolbar = findViewById(R.id.librootToolbar);
        if(toolbar!=null){
            toolbar.setTitle(""); //主题不显示，用自定义文本显示
            toolbar.setBackgroundColor(UtStatusBar.getToolbarBgColor());
            setSupportActionBar(toolbar);
            //
            Drawable drawableRetIc = ContextCompat.getDrawable(this, R.mipmap.ico_root_arrowback);
            if(drawableRetIc!=null){
//                drawableRetIc.setColorFilter(UtStatusBar.getToolbarCtColor(), PorterDuff.Mode.SRC_ATOP);
                PorterDuffColorFilter filter = new PorterDuffColorFilter(
                        UtStatusBar.getToolbarCtColor(),
                        PorterDuff.Mode.SRC_ATOP);
                drawableRetIc.setColorFilter(filter);
                ActionBar actionBar=getSupportActionBar();
                if(actionBar!=null){
                    actionBar.setHomeAsUpIndicator(drawableRetIc);
                }
            }
        }
        //统一设置全局背景
        UtRoot.setLayoutBg(this);
        //
        UtStack.self().addActivity(this);
        //配置返回键
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //
        initConfig(tvContent);
        //
        initViewAndDataAndEvent();
    }

    @SuppressWarnings({"NullableProblems", "ConstantConditions"})
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item!=null && (android.R.id.home==item.getItemId())){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 放入接口引擎里实现：友盟统计
        // MobclickAgent.onResume(this);
        if(inteRootEngineLifecycle!=null) {
            inteRootEngineLifecycle.onResumeRoot(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 放入接口引擎里实现：友盟统计
        // MobclickAgent.onPause(this);
        if(inteRootEngineLifecycle!=null) {
            inteRootEngineLifecycle.onPauseRoot(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(inteRootEngineLifecycle!=null) {
            inteRootEngineLifecycle.onStopRoot(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(inteRootEngineLifecycle!=null) {
            inteRootEngineLifecycle.onDestroyRoot(this);
        }
        // 关闭对话框
        loadingShut();
        if (dialogMsgBox != null) {
            dialogMsgBox.dismiss();
            dialogMsgBox=null;
        }
        //
        inteRootEngineLifecycle = null;
        UtStack.self().removeActivity(this);
    }

    @Override
    public Resources getResources() {
        // 字体不随系统字体改变
        Resources res = super.getResources();
        Configuration configuration = res.getConfiguration();
        if (configuration.fontScale != 1.0f) {
            configuration.fontScale = 1.0f;
            res.updateConfiguration(configuration, res.getDisplayMetrics());
        }
        return res;
    }


    /**=======================================
     * 作者：WangZezhi  (2018/2/26  14:51)
     * 功能：IBaseView实现方法
     * 描述：
     * ======================================= */
    @Override
    public void loadingShow(String msg) {
        loadingShut();
        dialogLoading=new DialogLoad(this, msg);
        dialogLoading.show();
    }

    @Override
    public void loadingShow(int intStrId) {
        loadingShut();
        dialogLoading=new DialogLoad(this, getString(intStrId));
        dialogLoading.show();
    }

    @Override
    public void loadingShut() {
        if(dialogLoading!=null){
            dialogLoading.dismiss();
        }
        dialogLoading=null;
    }

    /*=======================================
     * 作者：WangZezhi  (2019-12-05  15:26)
     * 功能：对话框部分
     * 描述：
     *=======================================*/
    @Override
    public void msgBoxShow(String msg) {
        if (dialogMsgBox != null && dialogMsgBox.isShowing()) {
            dialogMsgBox.dismiss();
        }
        dialogMsgBox = new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton(R.string.libroot_dialog_ent, null)
                .setCancelable(false)
                .create();
        dialogMsgBox.show();
    }

    @Override
    public void msgBoxShow(String title, String msg) {
        if (dialogMsgBox != null && dialogMsgBox.isShowing()) {
            dialogMsgBox.dismiss();
        }
        dialogMsgBox = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.libroot_dialog_ent, null)
                .setCancelable(false)
                .create();
        dialogMsgBox.show();
    }

    @Override
    public void msgBoxShow(String msg, String strEnt, DialogInterface.OnClickListener onClickListener) {
        if (dialogMsgBox != null) {
            dialogMsgBox.dismiss();
        }
        dialogMsgBox = new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton(strEnt, onClickListener)
                .setCancelable(false)
                .create();
        dialogMsgBox.show();
    }

    @Override
    public void msgBoxShow(String title, String msg, String strEnt, DialogInterface.OnClickListener onClickListener) {
        if (dialogMsgBox != null) {
            dialogMsgBox.dismiss();
        }
        dialogMsgBox = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                //取消
                .setCancelable(false)
                //确定
                .setPositiveButton(strEnt, onClickListener)
                .setCancelable(false)
                .create();
        dialogMsgBox.show();
    }

    @Override
    public void msgBoxShow(String title, String msg, String strEsc, String strEnt, DialogInterface.OnClickListener onClickListener) {
        if (dialogMsgBox != null) {
            dialogMsgBox.dismiss();
        }
        dialogMsgBox = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                //取消
                .setNegativeButton(strEsc, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogMsgBox.dismiss();
                    }
                })
                //确定
                .setPositiveButton(strEnt, onClickListener)
                .setCancelable(false)
                .create();
        dialogMsgBox.show();
    }

    @Override
    public void msgBoxShow(String title, String msg, String strEsc, DialogInterface.OnClickListener onClickListenerEsc, String strEnt, DialogInterface.OnClickListener onClickListenerEnt) {
        if (dialogMsgBox != null) {
            dialogMsgBox.dismiss();
        }
        dialogMsgBox = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                //取消
                .setNegativeButton(strEsc, onClickListenerEsc)
                //确定
                .setPositiveButton(strEnt, onClickListenerEnt)
                .setCancelable(false)
                .create();
        dialogMsgBox.show();
    }

    /**=======================================
     * 作者：WangZezhi  (2018/6/13  15:13)
     * 功能：  安卓动态权限处理部分
     * 描述：
     *=======================================*/
    //=====================电话、存储、相机、位置======================
    //校验phone、storage动态权限
    @SuppressWarnings("unused")
    protected void permissionDhCcXjWz(){
        RootActivityPermissionsDispatcher.dhCcXjWzNeedWithPermissionCheck(this);
    }
    //用户允许打开权限后执行本方法
    @NeedsPermission({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION})
    void dhCcXjWzNeed() {
        //UtRoot.toastLong("Need");
        //客户允许时，走本方法，这里无需做任何操作
    }
    //描述、阐述、基本原理
    @OnShowRationale({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION})
    void dhCcXjWzRationale(final PermissionRequest request) {
        if(request==null){
            return;
        }
        request.proceed();
    }
    //拒绝
    @OnPermissionDenied({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION})
    void dhCcXjWzDenied() {
        msgBoxShow(getString(R.string.permission_denied));
    }
    //用户勾选“不再询问”，则调用该方法（此时权限被拒绝）
    @OnNeverAskAgain({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION})
    void dhCcXjWzNever() {
        permissionSet();
    }

    //=====================电话、存储、相机======================
    //校验phone、storage动态权限
    @SuppressWarnings("unused")
    protected void permissionDhCcXj(){
        RootActivityPermissionsDispatcher.dhCcXjWzNeedWithPermissionCheck(this);
    }
    //用户允许打开权限后执行本方法
    @NeedsPermission({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void dhCcXjNeed() {
        //UtRoot.toastLong("Need");
        //客户允许时，走本方法，这里无需做任何操作
    }
    //描述、阐述、基本原理
    @OnShowRationale({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void dhCcXjRationale(final PermissionRequest request) {
        if(request==null){
            return;
        }
        request.proceed();
    }
    //拒绝
    @OnPermissionDenied({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void dhCcXjDenied() {
        msgBoxShow(getString(R.string.permission_denied));
    }
    //用户勾选“不再询问”，则调用该方法（此时权限被拒绝）
    @OnNeverAskAgain({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void dhCcXjNever() {
        permissionSet();
    }

    //=====================电话======================
    //校验phone动态权限
    @SuppressWarnings("unused")
    protected void permissionPhone(){
        RootActivityPermissionsDispatcher.phoneNeedWithPermissionCheck(this);
    }
    //用户允许打开权限后执行本方法
    @NeedsPermission({Manifest.permission.READ_PHONE_STATE})
    void phoneNeed() {
        //UtRoot.toastLong("Need");
        //客户允许时，走本方法，这里无需做任何操作
    }
    //描述、阐述、基本原理
    @OnShowRationale({Manifest.permission.READ_PHONE_STATE})
    void phoneRationale(final PermissionRequest request) {
        if(request==null){
            return;
        }
        request.proceed();
    }
    //拒绝
    @OnPermissionDenied({Manifest.permission.READ_PHONE_STATE})
    void phoneDenied() {
        msgBoxShow(getString(R.string.permission_denied));
    }
    //用户勾选“不再询问”，则调用该方法（此时权限被拒绝）
    @OnNeverAskAgain({Manifest.permission.READ_PHONE_STATE})
    void phoneNever() {
        permissionSet();
    }

    //=====================存储======================
    //校验storage动态权限
    @SuppressWarnings("unused")
    protected void permissionStorage(){
        RootActivityPermissionsDispatcher.storageNeedWithPermissionCheck(this);
    }
    //用户允许打开权限后执行本方法
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void storageNeed() {
        //UtRoot.toastLong("Need");
        //客户允许时，走本方法，这里无需做任何操作
    }
    //描述、阐述、基本原理
    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void storageRationale(final PermissionRequest request) {
        if(request==null){
            return;
        }
        request.proceed();
    }
    //拒绝
    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void storageDenied() {
        msgBoxShow(getString(R.string.permission_denied));
    }
    //用户勾选“不再询问”，则调用该方法（此时权限被拒绝）
    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void storageNever() {
        permissionSet();
    }

    //=====================相机权限管理部分======================
    //校验camera动态权限
    @SuppressWarnings("unused")
    protected void permissionCamera(){
        RootActivityPermissionsDispatcher.cameraNeedWithPermissionCheck(this);
    }
    //用户允许打开权限后执行本方法
    @NeedsPermission(Manifest.permission.CAMERA)
    void cameraNeed() {
        //UtRoot.toastLong("Need");
        //客户允许时，走本方法，这里无需做任何操作
    }
    //描述、阐述、基本原理
    @OnShowRationale(Manifest.permission.CAMERA)
    void cameraRationale(final PermissionRequest request) {
        if(request==null){
            return;
        }
        request.proceed();
    }
    //拒绝
    @OnPermissionDenied(Manifest.permission.CAMERA)
    void cameraDenied() {
        msgBoxShow(getString(R.string.permission_denied));
    }
    //用户勾选“不再询问”，则调用该方法（此时权限被拒绝）
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void cameraNever() {
        permissionSet();
    }

    //=====================定位权限管理部分======================
    //校验camera动态权限
    @SuppressWarnings("unused")
    protected void permissionLocation(){
        RootActivityPermissionsDispatcher.locationNeedWithPermissionCheck(this);
    }
    //用户允许打开权限后执行本方法
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    void locationNeed() {
        //UtRoot.toastLong("Need");
        //客户允许时，走本方法，这里无需做任何操作
    }
    //描述、阐述、基本原理
    @OnShowRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
    void locationRationale(final PermissionRequest request) {
        if(request==null){
            return;
        }
        request.proceed();
    }
    //拒绝
    @OnPermissionDenied(Manifest.permission.ACCESS_COARSE_LOCATION)
    void locationDenied() {
        msgBoxShow(getString(R.string.permission_denied));
    }
    //用户勾选“不再询问”，则调用该方法（此时权限被拒绝）
    @OnNeverAskAgain(Manifest.permission.ACCESS_COARSE_LOCATION)
    void locationNever() {
        permissionSet();
    }

    //=====================联系人权限管理部分======================
    //校验contact动态权限
    @SuppressWarnings("unused")
    protected void permissionContact(){
        RootActivityPermissionsDispatcher.contactNeedWithPermissionCheck(this);
    }
    //用户允许打开权限后执行本方法
    @NeedsPermission(Manifest.permission.READ_CONTACTS)
    void contactNeed() {
        //UtRoot.toastLong("Need");
        //客户允许时，走本方法，这里无需做任何操作
    }
    //描述、阐述、基本原理
    @OnShowRationale(Manifest.permission.READ_CONTACTS)
    void contactRationale(final PermissionRequest request) {
        if(request==null){
            return;
        }
        request.proceed();
    }
    //拒绝
    @OnPermissionDenied(Manifest.permission.READ_CONTACTS)
    void contactDenied() {
        msgBoxShow(getString(R.string.permission_denied));
    }
    //用户勾选“不再询问”，则调用该方法（此时权限被拒绝）
    @OnNeverAskAgain(Manifest.permission.READ_CONTACTS)
    void contactNever() {
        permissionSet();
    }

    //======================通用方法=======================
    //显示授权描述对话框
    private void permissionSet(){
        //被拒绝并且不再提醒,提示用户去设置界面重新打开权限
        msgBoxShow(getString(R.string.permission_title),
                getString(R.string.permission_esc),
                getString(R.string.permission_ent),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        //根据包名打开对应的设置界面
                        intent.setData(Uri.parse("package:" + UtRoot.getPackName()));
                        startActivity(intent);
                    }
                });
    }

    /**=======================================
     * 作者：WangZezhi  (2018/6/13  15:46)
     * 功能：安卓动态权限自动生成的方法
     * 描述：
     *=======================================*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //备注：将权限处理委托给生成的方法
        RootActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


}
