package com.dazhi.libroot.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dazhi.libroot.R;

/**
 * 功能描述：用于配置状态条
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：17-6-21 下午5:11
 * 修改日期：17-6-21 下午5:11
 */
public class UtStatusBar {
    //按钮背景色、内容色
    @DrawableRes
    private static int LIBROOT_BUTTON_BG = R.drawable.selct_libroot_btsubmit;
    private static int LIBROOT_BUTTON_CT = Color.parseColor("#ffffff");
    //工具条背景色、内容色
    private static int LIBROOT_TOOLBAR_BG = Color.parseColor("#19a0ff");
    private static int LIBROOT_TOOLBAR_CT = Color.parseColor("#ffffff");
    //默认布局背景色
    private static int LIBROOT_LAYOUT_BG = Color.parseColor("#f8f8f8");

    /**
     * =======================================
     * 作者：WangZezhi  (2018/4/19  11:14)
     * 功能：按鈕背景配置部分
     * 描述：
     * =======================================
     */
    public static void setLibRootBtBgResId(@DrawableRes int intDrawableResId) {
        LIBROOT_BUTTON_BG = intDrawableResId;
    }

    public static int getLibRootBtBgResId() {
        return LIBROOT_BUTTON_BG;
    }

    public static void setLibRootBtCtColor(@ColorInt int intColor) {
        LIBROOT_BUTTON_CT = intColor;
    }

    public static int getLibRootBtCtColor() {
        return LIBROOT_BUTTON_CT;
    }

    /**
     * =======================================
     * 作者：WangZezhi  (2018/4/19  11:14)
     * 功能：狀態條全局背景及主題文本部分
     * 描述：
     * =======================================
     */
    public static void setToolbarBgColor(@ColorInt int intColor) {
        LIBROOT_TOOLBAR_BG = intColor;
    }

    public static int getToolbarBgColor() {
        return LIBROOT_TOOLBAR_BG;
    }

    public static void setToolbarCtColor(@ColorInt int intColor) {
        LIBROOT_TOOLBAR_CT = intColor;
    }

    public static int getToolbarCtColor() {
        return LIBROOT_TOOLBAR_CT;
    }

    /**
     * =======================================
     * 作者：WangZezhi  (2018/6/2  15:07)
     * 功能：
     * 描述：
     * //统一设置全局背景(在BaseActivity内统一调用)
     * UtRoot.setLayoutBg(this);
     * =======================================
     */
    public static int getLayoutBgColor() {
        return LIBROOT_LAYOUT_BG;
    }

    public static void setLayoutBgColor(@ColorInt int intColor) {
        LIBROOT_LAYOUT_BG = intColor;
    }

    /**
     * =======================================
     * 作者：WangZezhi  (2018/10/19  18:07)
     * 功能：此方法用于配置状态条颜色为透明,替代沉浸式xml样式,用了他无需再配置xml透明样式
     * 描述：布局配合android:fitsSystemWindows="true"使用
     * true则留出状态条的位置；
     * false则占用状态条位置，此时可用于放背景图片，状态栏显示在背景图片上,但是记得在
     * Activity的创建方法后面再调用一次UtStatusBar.setStatusBarImmersive(this);
     * =======================================
     */
    // 设置状态条为沉浸式； android:fitsSystemWindows="false"
    public static void setStatusBarImmersive(@NonNull Activity activity) {
        // api小于19处理部分
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        // 设置状态条颜色
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        // api大于等于21处理部分
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            View decorView = window.getDecorView();
            //两个flag要结合使用，表示让应用的主体内容占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //上面代码是开启沉浸式用的
            //直接设置状态栏颜色
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            // 设置半透明(沉浸式)
            WindowManager.LayoutParams attributes = window.getAttributes();
            if (attributes == null) {
                return;
            }
            attributes.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            window.setAttributes(attributes);
        }
    }
    // 设置状态条为非沉浸式(即默认的正常状态条)； android:fitsSystemWindows="true"
    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int color) {
        // api小于19处理部分
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        // 设置状态条颜色
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        // api大于等于21处理部分
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //直接设置状态栏颜色
            window.setStatusBarColor(color);
        } // else {
//            // 设置半透明(沉浸式)
//            WindowManager.LayoutParams attributes = window.getAttributes();
//            if (attributes == null) {
//                return;
//            }
//            attributes.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//            window.setAttributes(attributes);
//            // 设置状态栏颜色
//            // 增加占位状态栏
//            ViewGroup decorView = (ViewGroup) window.getDecorView();
//            View statusBarView = new View(activity);
//            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    getStatusBarHeight(activity));
//            statusBarView.setBackgroundColor(color);
//            decorView.addView(statusBarView, lp);
        // }
    }


}
