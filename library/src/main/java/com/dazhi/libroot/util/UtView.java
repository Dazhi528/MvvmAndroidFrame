package com.dazhi.libroot.util;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 界面操作工具类
 */
public class UtView {
    /**
     * 设置可输入小数点位数
     * @param editText 输入框对象
     * @param max      最大位数
     */
    public static void setFilter(EditText editText, final int max) {
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

    public static void setFocus(EditText editText) {
        if(editText==null){
            return;
        }
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    /**
     * 打开输入法
     */
    public static void keyboardShow(Context context, EditText editText) {
        if(context==null || editText==null){
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            // 接受软键盘输入的编辑文本或其它视图
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 关闭输入法
     */
    public static void keyboardHide(Context context, EditText editText) {
        if(context==null || editText==null){
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
        editText.clearFocus();
    }


}
