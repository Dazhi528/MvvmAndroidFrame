package com.dazhi.libroot.root

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.dazhi.libroot.R
import com.dazhi.libroot.inte.IRootView
import com.dazhi.libroot.ui.dialog.DialogLoad
import com.dazhi.libroot.util.RtCmn
import com.dazhi.libroot.util.RtConfig
import com.dazhi.libroot.util.RtStack
import com.dazhi.libroot.util.RtStatusBar
import permissions.dispatcher.*

/**
 * 功能：根Activity
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2019/2/26 17:28
 * 修改日期：2019/2/26 17:28
 */
@Suppress("unused")
@RuntimePermissions //标记需要运行时判断的类(用于动态生成代理类)
abstract class RootActivity<T : ViewBinding> : AppCompatActivity(), IRootView {
    private var dialogLoading //进度对话框
            : DialogLoad? = null
    private var dialogMsgBox //警告对话框
            : AlertDialog? = null

    // 依赖注入：生命周期引擎
    private var iRootEngineLifecycle = RtConfig.self().engineLifecycle

    /*==============抽象方法============*/ /**/
    protected fun initAtSetContentViewBefore() {}

    /*获得布局id*/
    protected lateinit var binding: T
    protected abstract fun initBinding(): T

    /*初始化配置等，如标题*/
    protected abstract fun initConfig(tvToolTitle: TextView?)

    /*初始化视图等*/
    protected abstract fun initViewAndDataAndEvent()

    // 设置为true为了解决部分华为手机按Home后，点应用图标，应用重启问题
    override fun moveTaskToBack(nonRoot: Boolean): Boolean {
        return super.moveTaskToBack(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        if (iRootEngineLifecycle != null) {
            iRootEngineLifecycle!!.onCreateRoot(this)
        }
        //
        initAtSetContentViewBefore()
        // 当要设置view，而不是资源ID时，需实现getLayoutId回0，
        // 并覆盖getLayoutView方法，回实际的view
        binding = initBinding()
        setContentView(binding.root)
        //
        val tvContent = findViewById<TextView>(R.id.librootToolbarTitle)
        tvContent?.setTextColor(RtStatusBar.getToolbarCtColor())
        //状态条配置
        RtStatusBar.setStatusBarColor(this, RtStatusBar.getToolbarBgColor())
        //
        val toolbar = findViewById<Toolbar>(R.id.librootToolbar)
        if (toolbar != null) {
            toolbar.title = "" //主题不显示，用自定义文本显示
            toolbar.setBackgroundColor(RtStatusBar.getToolbarBgColor())
            setSupportActionBar(toolbar)
            //
            val drawableRetIc = ContextCompat.getDrawable(this, R.mipmap.ico_root_arrowback)
            if (drawableRetIc != null) {
//                drawableRetIc.setColorFilter(UtStatusBar.getToolbarCtColor(), PorterDuff.Mode.SRC_ATOP);
                val filter = PorterDuffColorFilter(
                        RtStatusBar.getToolbarCtColor(),
                        PorterDuff.Mode.SRC_ATOP)
                drawableRetIc.colorFilter = filter
                val actionBar = supportActionBar
                actionBar?.setHomeAsUpIndicator(drawableRetIc)
            }
        }
        //统一设置全局背景
        RtCmn.setLayoutBg(this)
        //
        RtStack.self().addActivity(this)
        //配置返回键
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        //
        initConfig(tvContent)
        //
        initViewAndDataAndEvent()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (android.R.id.home == item.itemId) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        // 放入接口引擎里实现：友盟统计
        // MobclickAgent.onResume(this);
        if (iRootEngineLifecycle != null) {
            iRootEngineLifecycle!!.onResumeRoot(this)
        }
    }

    override fun onPause() {
        super.onPause()
        // 放入接口引擎里实现：友盟统计
        // MobclickAgent.onPause(this);
        if (iRootEngineLifecycle != null) {
            iRootEngineLifecycle!!.onPauseRoot(this)
        }
    }

    override fun onStop() {
        super.onStop()
        if (iRootEngineLifecycle != null) {
            iRootEngineLifecycle!!.onStopRoot(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (iRootEngineLifecycle != null) {
            iRootEngineLifecycle!!.onDestroyRoot(this)
        }
        // 关闭对话框
        loadingShut()
        if (dialogMsgBox != null) {
            dialogMsgBox!!.dismiss()
            dialogMsgBox = null
        }
        //
        iRootEngineLifecycle = null
        RtStack.self().removeActivity(this)
    }

    override fun getResources(): Resources {
        // 字体不随系统字体改变
        val res = super.getResources()
        val configuration = res.configuration
        if (configuration.fontScale != 1.0f) {
            configuration.fontScale = 1.0f
            res.updateConfiguration(configuration, res.displayMetrics)
        }
        return res
    }

    /**=======================================
     * 作者：WangZezhi  (2018/2/26  14:51)
     * 功能：IBaseView实现方法
     * 描述：
     * =======================================  */
    override fun loadingShow(msg: String?) {
        loadingShut()
        dialogLoading = DialogLoad(this, msg)
        dialogLoading?.show()
    }

    override fun loadingShow(intStrId: Int) {
        loadingShut()
        dialogLoading = DialogLoad(this, getString(intStrId))
        dialogLoading?.show()
    }

    override fun loadingShut() {
        if (dialogLoading != null) {
            dialogLoading!!.dismiss()
        }
        dialogLoading = null
    }

    /*=======================================
     * 作者：WangZezhi  (2019-12-05  15:26)
     * 功能：对话框部分
     * 描述：
     *=======================================*/
    override fun msgBoxShow(msg: String, title: String?,
                            strEsc: String?, onClickListenerEsc: DialogInterface.OnClickListener?,
                            strEnt: String?, onClickListenerEnt: DialogInterface.OnClickListener?) {
        if (dialogMsgBox != null) {
            dialogMsgBox!!.dismiss()
        }
        dialogMsgBox = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                // 取消按钮配置
                .setNegativeButton(strEsc,
                        if (onClickListenerEsc == null && !strEsc.isNullOrEmpty()) DialogInterface
                                .OnClickListener { _, _ -> dialogMsgBox!!.dismiss() }
                        else onClickListenerEsc)
                // 确定按钮配置
                .setPositiveButton(strEnt
                        ?: getString(R.string.libroot_dialog_ent), onClickListenerEnt) //确定
                .setCancelable(false)
                .create()
        dialogMsgBox!!.show()
    }

    /**=======================================
     * 作者：WangZezhi  (2018/6/13  15:13)
     * 功能：  安卓动态权限处理部分
     * 描述：
     * ======================================= */
    //=====================电话、存储、相机、位置======================
    //校验phone、storage动态权限
    protected fun permissionDhCcXjWz() {
        dhCcXjWzNeedWithPermissionCheck()
    }

    //用户允许打开权限后执行本方法
    @NeedsPermission(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION)
    fun dhCcXjWzNeed() {
        //UtRoot.toastLong("Need");
        //客户允许时，走本方法，这里无需做任何操作
    }

    //描述、阐述、基本原理
    @OnShowRationale(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION)
    fun dhCcXjWzRationale(request: PermissionRequest?) {
        if (request == null) {
            return
        }
        request.proceed()
    }

    //拒绝
    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION)
    fun dhCcXjWzDenied() {
        msgBoxShow(getString(R.string.permission_denied))
    }

    //用户勾选“不再询问”，则调用该方法（此时权限被拒绝）
    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION)
    fun dhCcXjWzNever() {
        permissionSet()
    }

    //=====================电话、存储、相机======================
    //校验phone、storage动态权限
    protected fun permissionDhCcXj() {
        dhCcXjWzNeedWithPermissionCheck()
    }

    //用户允许打开权限后执行本方法
    @NeedsPermission(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun dhCcXjNeed() {
        //UtRoot.toastLong("Need");
        //客户允许时，走本方法，这里无需做任何操作
    }

    //描述、阐述、基本原理
    @OnShowRationale(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun dhCcXjRationale(request: PermissionRequest?) {
        if (request == null) {
            return
        }
        request.proceed()
    }

    //拒绝
    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun dhCcXjDenied() {
        msgBoxShow(getString(R.string.permission_denied))
    }

    //用户勾选“不再询问”，则调用该方法（此时权限被拒绝）
    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun dhCcXjNever() {
        permissionSet()
    }

    //=====================电话======================
    //校验phone动态权限
    protected fun permissionPhone() {
        phoneNeedWithPermissionCheck()
    }

    //用户允许打开权限后执行本方法
    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    fun phoneNeed() {
        //UtRoot.toastLong("Need");
        //客户允许时，走本方法，这里无需做任何操作
    }

    //描述、阐述、基本原理
    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    fun phoneRationale(request: PermissionRequest?) {
        if (request == null) {
            return
        }
        request.proceed()
    }

    //拒绝
    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    fun phoneDenied() {
        msgBoxShow(getString(R.string.permission_denied))
    }

    //用户勾选“不再询问”，则调用该方法（此时权限被拒绝）
    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    fun phoneNever() {
        permissionSet()
    }

    //=====================存储======================
    //校验storage动态权限
    protected fun permissionStorage() {
        storageNeedWithPermissionCheck()
    }

    //用户允许打开权限后执行本方法
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun storageNeed() {
        //UtRoot.toastLong("Need");
        //客户允许时，走本方法，这里无需做任何操作
    }

    //描述、阐述、基本原理
    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun storageRationale(request: PermissionRequest?) {
        if (request == null) {
            return
        }
        request.proceed()
    }

    //拒绝
    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun storageDenied() {
        msgBoxShow(getString(R.string.permission_denied))
    }

    //用户勾选“不再询问”，则调用该方法（此时权限被拒绝）
    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun storageNever() {
        permissionSet()
    }

    //=====================相机权限管理部分======================
    //校验camera动态权限
    protected fun permissionCamera() {
        cameraNeedWithPermissionCheck()
    }

    //用户允许打开权限后执行本方法
    @NeedsPermission(Manifest.permission.CAMERA)
    fun cameraNeed() {
        //UtRoot.toastLong("Need");
        //客户允许时，走本方法，这里无需做任何操作
    }

    //描述、阐述、基本原理
    @OnShowRationale(Manifest.permission.CAMERA)
    fun cameraRationale(request: PermissionRequest?) {
        if (request == null) {
            return
        }
        request.proceed()
    }

    //拒绝
    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun cameraDenied() {
        msgBoxShow(getString(R.string.permission_denied))
    }

    //用户勾选“不再询问”，则调用该方法（此时权限被拒绝）
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun cameraNever() {
        permissionSet()
    }

    //=====================定位权限管理部分======================
    //校验camera动态权限
    protected fun permissionLocation() {
        locationNeedWithPermissionCheck()
    }

    //用户允许打开权限后执行本方法
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun locationNeed() {
        //UtRoot.toastLong("Need");
        //客户允许时，走本方法，这里无需做任何操作
    }

    //描述、阐述、基本原理
    @OnShowRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun locationRationale(request: PermissionRequest?) {
        if (request == null) {
            return
        }
        request.proceed()
    }

    //拒绝
    @OnPermissionDenied(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun locationDenied() {
        msgBoxShow(getString(R.string.permission_denied))
    }

    //用户勾选“不再询问”，则调用该方法（此时权限被拒绝）
    @OnNeverAskAgain(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun locationNever() {
        permissionSet()
    }

    //=====================联系人权限管理部分======================
    //校验contact动态权限
    protected fun permissionContact() {
        contactNeedWithPermissionCheck()
    }

    //用户允许打开权限后执行本方法
    @NeedsPermission(Manifest.permission.READ_CONTACTS)
    fun contactNeed() {
        //UtRoot.toastLong("Need");
        //客户允许时，走本方法，这里无需做任何操作
    }

    //描述、阐述、基本原理
    @OnShowRationale(Manifest.permission.READ_CONTACTS)
    fun contactRationale(request: PermissionRequest?) {
        if (request == null) {
            return
        }
        request.proceed()
    }

    //拒绝
    @OnPermissionDenied(Manifest.permission.READ_CONTACTS)
    fun contactDenied() {
        msgBoxShow(getString(R.string.permission_denied))
    }

    //用户勾选“不再询问”，则调用该方法（此时权限被拒绝）
    @OnNeverAskAgain(Manifest.permission.READ_CONTACTS)
    fun contactNever() {
        permissionSet()
    }

    //======================通用方法=======================
    //显示授权描述对话框
    private fun permissionSet() {
        //被拒绝并且不再提醒,提示用户去设置界面重新打开权限
        msgBoxShow(getString(R.string.permission_title),
                getString(R.string.permission_esc),
                getString(R.string.permission_ent)
        ) { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            //根据包名打开对应的设置界面
            intent.data = Uri.parse("package:" + RtCmn.getPackName())
            startActivity(intent)
        }
    }

    /**=======================================
     * 作者：WangZezhi  (2018/6/13  15:46)
     * 功能：安卓动态权限自动生成的方法
     * 描述：
     * ======================================= */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //备注：将权限处理委托给生成的方法
        onRequestPermissionsResult(requestCode, grantResults)
    }

}