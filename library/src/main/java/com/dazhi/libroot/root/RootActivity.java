package com.dazhi.libroot.root;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import com.dazhi.libroot.R;
import com.dazhi.libroot.inte.InteRootView;
import com.dazhi.libroot.ui.dialog.DialogLoad;
import com.dazhi.libroot.util.UtRoot;
import com.dazhi.libroot.util.UtStack;
import com.dazhi.libroot.util.UtStatusBar;
import com.umeng.analytics.MobclickAgent;
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

    /*==============抽象方法============*/
    /*获得布局id*/
    protected abstract int getLayoutId();
    /*初始化配置等，如标题*/
    protected abstract void initConfig(TextView tvToolTitle);
    /*初始化视图等*/
    protected abstract void initViewAndDataAndEvent();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(getLayoutId());
        //
        TextView tvContent=findViewById(R.id.librootToolbarTitle);
        if(tvContent!=null){
            tvContent.setTextColor(UtStatusBar.getToolbarCtColor());
        }
        //状态条配置
        UtStatusBar.setStatusBarColor(this, UtStatusBar.getToolbarBgColor());
        //
        Toolbar toolbar= findViewById(R.id.librootToolbar);
        if(toolbar!=null){
            toolbar.setTitle(""); //主题不显示，用自定义文本显示
            toolbar.setBackgroundColor(UtStatusBar.getToolbarBgColor());
            setSupportActionBar(toolbar);
            //
            Drawable drawableRetIc = ContextCompat.getDrawable(this, R.mipmap.ico_root_arrowback);
            if(drawableRetIc!=null){
                drawableRetIc.setColorFilter(UtStatusBar.getToolbarCtColor(), PorterDuff.Mode.SRC_ATOP);
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
        // 友盟统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 友盟统计
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭对话框
        loadingShut();
        if (dialogMsgBox != null) {
            dialogMsgBox.dismiss();
            dialogMsgBox=null;
        }
        //
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
    public void msgBoxShow(String msg, String strEsc, String strEnt, DialogInterface.OnClickListener onClickListener) {
        if (dialogMsgBox != null) {
            dialogMsgBox.dismiss();
        }
        dialogMsgBox = new AlertDialog.Builder(this)
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

    /**=======================================
     * 作者：WangZezhi  (2018/6/13  15:13)
     * 功能：  安卓动态权限处理部分
     * 描述：
     *=======================================*/
    //=====================电话、存储、相机、位置======================
    //校验phone、storage动态权限
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
