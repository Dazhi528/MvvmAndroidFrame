package com.dazhi.libroot.root;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dazhi.libroot.R;
import com.dazhi.libroot.inte.InteRootView;
import com.dazhi.libroot.ui.dialog.DialogLoad;
import com.dazhi.libroot.util.UtRoot;

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2019/2/27 14:19
 * 修改日期：2019/2/27 14:19
 */
public abstract class RootFragment extends Fragment implements InteRootView {
    protected Activity activity;
    protected View view;
    //
    private DialogLoad dialogLoading; //进度对话框
    private AlertDialog dialogMsgBox; //警告对话框


    /*==============抽象方法============*/
    /*获得布局id*/
    protected abstract int getLayoutId();
    /*初始化配置*/
    protected void initConfig(){ }
    /*初始化视图等*/
    protected abstract void initViewAndDataAndEvent();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //统一设置全局背景
        UtRoot.setLayoutBg(activity);
        //
        initConfig();
        initViewAndDataAndEvent();
    }


    /**=======================================
     * 作者：WangZezhi  (2018/2/26  14:51)
     * 功能：IBaseView实现方法
     * 描述：
     *=======================================*/
    @Override
    public void loadingShow(String msg) {
        loadingShut();
        if(activity==null){
            return;
        }
        dialogLoading=new DialogLoad(activity, msg);
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
        if(activity==null){
            return;
        }
        dialogMsgBox = new AlertDialog.Builder(activity)
                //.setTitle(getString(R.string.libroot_dialogedit_title))
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
        if(activity==null){
            return;
        }
        dialogMsgBox = new AlertDialog.Builder(activity)
                //.setTitle(getString(R.string.libroot_dialogedit_title))
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
        if(activity==null){
            return;
        }
        dialogMsgBox = new AlertDialog.Builder(activity)
                //.setTitle(getString(R.string.libroot_dialogedit_title))
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


}
