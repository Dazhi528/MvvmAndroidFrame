package com.dazhi.libroot.root

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dazhi.libroot.R
import com.dazhi.libroot.inte.IRootView
import com.dazhi.libroot.ui.dialog.DialogLoad
import com.dazhi.libroot.util.RtCmn

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2019/2/27 14:19
 * 修改日期：2019/2/27 14:19
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class RootFragment<T : ViewBinding> : Fragment(), IRootView {
    @Suppress("LeakingThis")
    private var _binding: T? = null
    protected val binding get() = _binding!!

    protected var activity: RootActivity<*>? = null

    //
    private var dialogLoading //进度对话框
            : DialogLoad? = null
    private var dialogMsgBox //警告对话框
            : AlertDialog? = null

    /*==============抽象方法============*/ /*获得布局id*/
    protected abstract fun initBinding(container: ViewGroup?): T

    /*初始化配置*/
    protected abstract fun initConfig()

    /*初始化视图等*/
    protected abstract fun initViewAndDataAndEvent()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as? RootActivity<*>?
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        mview = inflater.inflate(layoutId, container, false)
// 子类： _binding = ResultProfileBinding.inflate(inflater, container, false)
        _binding = initBinding(container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        activity = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //统一设置全局背景
        RtCmn.setLayoutBg(activity)
        //
        initConfig()
        initViewAndDataAndEvent()
    }

    /**=======================================
     * 作者：WangZezhi  (2018/2/26  14:51)
     * 功能：IBaseView实现方法
     * 描述：
     * ======================================= */
    override fun loadingShow(msg: String?) {
        loadingShut()
        if (activity == null) {
            return
        }
        dialogLoading = DialogLoad(activity, msg)
        dialogLoading!!.show()
    }

    override fun loadingShow(intStrId: Int) {
        loadingShut()
        if (activity == null) {
            return
        }
        dialogLoading = DialogLoad(activity, RtCmn.getString(intStrId))
        dialogLoading!!.show()
    }

    override fun loadingShut() {
        if (dialogLoading != null) {
            dialogLoading!!.dismiss()
        }
        dialogLoading = null
    }

    /*=======================================
     * 作者：WangZezhi  (2019-12-05  15:42)
     * 功能：对话框部分
     * 描述：
     *=======================================*/
    override fun msgBoxShow(msg: String, title: String?,
                            strEsc: String?, onClickListenerEsc: DialogInterface.OnClickListener?,
                            strEnt: String?, onClickListenerEnt: DialogInterface.OnClickListener?) {
        if (dialogMsgBox != null) {
            dialogMsgBox!!.dismiss()
        }
        if (activity == null) {
            return
        }
        dialogMsgBox = AlertDialog.Builder(activity!!)
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

}