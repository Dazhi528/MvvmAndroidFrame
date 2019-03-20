package com.dazhi.libroot.util;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 界面操作工具类
 */
public class UtView {
    /**
     * 设置可输入小数点位数
     * @param editText 输入框对象
     * @param max      最大位数
     */
    public static void setFilters(EditText editText, final int max) {
        editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        // 设置字符过滤
        editText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int mlength = dest.toString().substring(index+1).length();
                    if (mlength == max) {
                        return "";
                    }
                }
                return null;
            }
        }});
    }

    public static void setFocus(EditText et) {
        if(et==null){
            return;
        }
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
    }

    /**
     * 打开输入法
     */
    public static void keyboardOpen(Context context, EditText editText) {
        if(context==null || editText==null){
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            // 接受软键盘输入的编辑文本或其它视图
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 关闭输入法
     */
    public static void keyboardClose(Context context) {
        if(context==null){
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(((Activity) context).getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 判断输入法是否已经打开
     */
//    public static boolean booKeyboard(Context context) {
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        return imm.isActive();
//    }


}
