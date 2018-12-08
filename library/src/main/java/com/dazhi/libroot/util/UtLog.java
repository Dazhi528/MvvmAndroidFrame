package com.dazhi.libroot.util;

import android.util.Log;

/**
 * 功能：日志工具
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/5/2 10:41
 * 修改日期：2018/5/2 10:41
 */
public class UtLog {
    private static boolean booDebug=false; //默认关闭

    public static void setOpen(){
        booDebug=true;
    }
    public static void setClose(){
        booDebug=false;
    }

    /**=======================================
     * 作者：WangZezhi  (2018/5/2  10:45)
     * 功能：
     * 描述：
     *=======================================*/
    public static void d(String strMsg){
        d("UtLogD", strMsg);
    }
    public static void d(String strTag, String strMsg){
        if(!booDebug){
            return;
        }
        Log.d(strTag, strMsg);
    }

    public static void i(String strMsg){
        i("UtLogI", strMsg);
    }
    public static void i(String strTag, String strMsg){
        if(!booDebug){
            return;
        }
        Log.i("UtLogI", strMsg);
    }

    public static void e(String strMsg){
        e("UtLogE", strMsg);
    }
    public static void e(String strTag, String strMsg){
        if(!booDebug){
            return;
        }
        Log.e("UtLogE", strMsg);
    }


}
