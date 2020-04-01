package com.dazhi.libroot.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.dazhi.libroot.R;
import com.dazhi.libroot.inte.InteRootCall;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding3.view.RxView;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import io.reactivex.functions.Consumer;
import static android.content.Context.INPUT_METHOD_SERVICE;

@SuppressWarnings("ALL")
public class UtRoot {
    private static Context context;

    private UtRoot() {
        throw new UnsupportedOperationException("you can't instantiate me");
    }


    public static void initApp(Context context) {
        UtRoot.context = context;
    }

    /**
     * 获取ApplicationContext
     * @return ApplicationContext
     */
    public static Context getAppContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("you should init first");
    }

    /**=======================================
     * 作者：WangZezhi  (2018/5/23  14:59)
     * 功能：  获得xml资源
     * 描述：
     *=======================================*/
    public static String getString(@StringRes int id) {
        return context.getString(id);
    }
    public static int getColor(@ColorRes int iId){
        return ResourcesCompat.getColor(context.getResources(), iId, null);
    }
    public static Drawable getDrawable(@DrawableRes int iId){
        return ResourcesCompat.getDrawable(context.getResources(), iId, null);
    }

    /**=======================================
     * 作者：WangZezhi  (2018/7/11  09:09)
     * 功能：
     * 描述：getDimension()返回的是float，其余两个返回的是int
     * getDimensionPixelSize()返回的是实际数值的四舍五入
     * getDimensionPixelOffset返回的是实际数值去掉后面的小数点;
     * 三个函数返回的都是像素(即dip值乘以屏幕密度),如果你在资源文件中定义的长度单位不是dip，
     * 而是px的话，程序会直接抛出异常或原样输出，具体需自己测试。
     *=======================================*/
    public static int getPx(@DimenRes int intId){
        return context.getResources().getDimensionPixelSize(intId);
    }

    /**********************************************
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dpToPx(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f); //加0.5f相当于四舍五入
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int pxToDp(float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**=======================================
     * 作者：WangZezhi  (2019-05-23  17:11)
     * 功能：
     * 描述：
     *=======================================*/
    public static void rxViewClick(@NonNull View view, @NonNull final InteRootCall inteCallRoot) {
        RxView.clicks(view)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) {
                        inteCallRoot.call();
                    }
                });
    }

    /**=======================================
     * 作者：WangZezhi  (2018/5/24  15:18)
     * 功能：设置最顶层view背景
     * 描述：getDecorView 获得window最顶层的View
     *=======================================*/
    public static void setLayoutBg(Activity activity){
        if(activity==null){
            return;
        }
        View view = activity.getWindow().getDecorView();
        view.setBackgroundColor(UtStatusBar.getLayoutBgColor());
    }

    /**=======================================
     * 作者：WangZezhi  (2018/4/27  14:15)
     * 功能：设置屏幕的宽高
     * 描述：Theme.Dialog View高度；在setContentView(id)之后添加以下代码
     *=======================================*/
    public static void setWHActivity(Activity activity, float floScaleW, float floScaleH) {
        try {
            Window window = activity.getWindow();
            Display display = window.getWindowManager().getDefaultDisplay();
            android.view.WindowManager.LayoutParams wmLp = window.getAttributes();
            wmLp.width = (int) (display.getWidth() * floScaleW);
            wmLp.height = (int) (display.getHeight() * floScaleH);
            window.setAttributes(wmLp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // floScaleW 设置值要小于1，即：实际宽度是屏幕宽度的倍数
    // 调用实例：UtRoot.setDialogWidthScale(this, dialog, 0.8F)
    public static void setDialogWidthScale(Activity activity, Dialog dialog, float floScaleW) {
        if(activity==null || dialog==null || floScaleW<=0 || floScaleW>1) {
            return;
        }
        try {
            Window winScreen = activity.getWindow();
            Display displayScreen = winScreen.getWindowManager().getDefaultDisplay();
            int intWidth=(int) (displayScreen.getWidth() * floScaleW); // 要显示的宽度
            //
            Window winDialog = dialog.getWindow();
            assert winDialog != null;
            android.view.WindowManager.LayoutParams wmLp = winDialog.getAttributes();
            wmLp.width = intWidth;
            winDialog.setAttributes(wmLp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**=======================================
     * 作者：WangZezhi  (2018/10/17  09:54)
     * 功能：设置对话框最小、最大高度
     * 描述：单位是像素，建议调用本工具的getPx(@DimenRes int intId)获得资源文件的dp
     *=======================================*/
    public static void setDialogMaxHeight(Context context, Dialog dialog, int intMaxH) {
        if (context == null || dialog == null ||  intMaxH <= 0 ) {
            return;
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return;
        }
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        if(metrics.heightPixels> intMaxH) {
            // 如果高度大于最大值，则设置最大值
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, intMaxH);
        } else {
            // 设置适应
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }

    public static void setDialogMinMaxHeight(Context context, Dialog dialog, int intMinH, int intMaxH) {
        if (context == null || dialog == null || intMinH<=0 || intMaxH <= 0 || intMaxH < intMinH) {
            return;
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return;
        }
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        // 如果高度小于最小，则设置最小值
        if(metrics.heightPixels< intMinH) {
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, intMinH);
            return;
        }
        // 如果高度大于最大值，则设置最大值
        if(metrics.heightPixels> intMaxH) {
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, intMaxH);
            return;
        }
        // 在最大值和最小值之间，则设置适应
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    /**=======================================
     * 作者：WangZezhi  (2019-05-21  14:03)
     * 功能：四舍五入保留intScale位小数
     * 描述：在实际应用中发现返回值是double时，应用可能会变形，因此统一返回String
     *=======================================*/
    public static String roundHalfUp(float floNumber, int intScale){
        BigDecimal bigDecimal=new BigDecimal(floNumber);
        return roundHalfUp(bigDecimal, intScale);
    }
    public static String roundHalfUp(double douNumber, int intScale){
        BigDecimal bigDecimal=new BigDecimal(douNumber);
        return roundHalfUp(bigDecimal, intScale);
    }
    public static String roundHalfUp(String strNumber, int intScale){
        BigDecimal bigDecimal=new BigDecimal(strNumber);
        return roundHalfUp(bigDecimal, intScale);
    }
    public static String roundHalfUp(BigDecimal bigDecimal, int intScale){
        if(bigDecimal==null || intScale<0) {
            return "0";
        }
        return bigDecimal.setScale(intScale, BigDecimal.ROUND_HALF_UP).toString();
    }

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
     * 显示/隐藏输入法
     */
    public static void keyboardToggle(@NonNull Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if(view==null){
            return;
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            // 接受软键盘输入的编辑文本或其它视图
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示输入法
     */
    public static void keyboardShow(View view) {
        if(view==null || !view.isFocusable()){
            return;
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            // 接受软键盘输入的编辑文本或其它视图
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * 隐藏输入法
     */
    public static void keyboardHide(View view) {
        if(view==null || !view.isFocusable()){
            return;
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            // 接受软键盘输入的编辑文本或其它视图
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        view.clearFocus();
    }

    public static void keyboardHide(@NonNull Activity activity, boolean booClearFocus) {
        View view = activity.getWindow().peekDecorView();
        if(view==null){
            return;
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            // 接受软键盘输入的编辑文本或其它视图
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if(booClearFocus){
            view.clearFocus();
        }
    }

    /**
     * 描述：用于禁用系统软键盘但显示光标
     * 用法：仅创建方法中每个EditText调用一次即可
     * 注意：要完成软键盘的启动与禁用，需要配合下方的keyboardCtrl方法
     *      即用此方法初始化后，用keyboardCtrl方法来控制键盘的启用与禁用
     */
    public static void keyboardDisableOnlyInit(@NonNull EditText editText) {
        int curVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null; //4.0以上和4.2以上方法名有所改变
        if (curVersion >= 16) { // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (curVersion >= 14) {  // 4.0
            methodName = "setSoftInputShownOnFocus";
        }else { // 4.0以下采用此方法
            editText.setInputType(InputType.TYPE_NULL);
            return;
        }
        Class<EditText> cls = EditText.class;
        try {
            Method method = cls.getMethod(methodName, boolean.class);
            method.setAccessible(true);
            method.invoke(editText, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 描述：用于禁用系统软键盘但显示光标
     * 用法：仅创建方法中每个EditText调用一次即可
     */
    public static void keyboardCtrl(@NonNull Activity activity, boolean booUsable) {
        Window window=activity.getWindow();
        if(window==null) {
            return;
        }
        if(booUsable) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        }else {
            window.addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        }
    }

    /**
     * 描述：键盘是否显示
     * 用法：
     */
    public static boolean keyboardBooVisible(@NonNull Activity activity) {
        // 获取当前屏幕的真实高度(即：整个屏幕高度)
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        //     activity.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        // }
        // 获得当前屏幕的高度(此高度不一定等于实际屏幕高度；例：有底部虚拟按钮条时)
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int usableHeight = dm.heightPixels;
        //获取当前屏幕的可用区域框架(范围)
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int visibleHeight = rect.bottom;
        // 键盘弹窗后，显示高度(visibleHeight)会变小；反之当其大于等于可用高度时，键盘没显示
        return usableHeight > visibleHeight;
    }

    /*=======================================
     * 作者：WangZezhi  (2020/3/20  17:24)
     * 功能：检查网络是否连接
     * 描述：
     *=======================================*/
    public static Boolean booNetworkConnected() {
        if(context==null) {
            return false;
        }
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities networkCapabilities = manager
                    .getNetworkCapabilities(manager.getActiveNetwork());
            if (networkCapabilities != null) {
                return (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
            }
        } else {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }

    /**=======================================
     * 作者：WangZezhi  (2018/3/23  15:10)
     * 功能：
     * 描述：
     *=======================================*/
    public static String getPackName(){
        return context.getPackageName();
    }
    public static int getVersionCode() {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(getPackName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1; //表示未找到
        }
    }
    public static String getVersionName() {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(getPackName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }
    /**=======================================
     * 作者：WangZezhi  (2018/3/23  15:15)
     * 功能：
     * 描述：
     *=======================================*/
    /**
     * 调用此方法获得设备号
     */
    @SuppressLint("HardwareIds")
    public static String getDeviceId() {
        String IMEI = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                IMEI = telephonyManager != null ? telephonyManager.getDeviceId() : "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(TextUtils.isEmpty(IMEI)){
            IMEI = Settings.System.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        return IMEI==null ? "" : IMEI;
    }

    /**=======================================
     * 作者：WangZezhi  (2018/3/10  10:42)
     * 功能： 时间比较
     * 描述：
     * @param strDateSta
     * @param strDateEnd
     * @return 1大于 0等于 -1小于 250异常
     *=======================================*/
    public static int timeCompare(String strDateSta, String strDateEnd){
        final int intFail=250; //比较失败
        if(TextUtils.isEmpty(strDateSta) || TextUtils.isEmpty(strDateEnd)){
            return intFail;
        }
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
//        DateFormat df = DateFormat.getDateInstance();
        Date dateSta, dateEnd;
        try {
            dateSta=sdf.parse(strDateSta);
            dateEnd=sdf.parse(strDateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
            return intFail;
        }
        long lonDiff=dateEnd.getTime() - dateSta.getTime();
        if(lonDiff > 0){
            return 1;
        }
        if(lonDiff==0){
            return 0;
        }
        return -1;
    }

    /**
     * 作者：WangZezhi
     * 功能：获得几天前的时间（如果是今天零晨，则填0）
     * 详情：
     * @param iDaysAgo 几天前
     * @return 时间戳
     */
    public static String getDateStrByDaysAgo(int iDaysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - iDaysAgo);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
//        DateFormat df = DateFormat.getDateInstance();
        return sdf.format(calendar.getTime());
    }

    public static long timeStandardToStamp(String strTimeStandard){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
//        DateFormat df = DateFormat.getDateTimeInstance();
        long lonTime=0;
        try {
            lonTime=sdf.parse(strTimeStandard).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return lonTime;
        }
        return lonTime;
    }

    /**=======================================
     * 作者：WangZezhi  (2018/3/22  11:29)
     * 功能：
     * 描述：
     * @param strDate : "yyyy-MM-dd"
     *=======================================*/
    public static String getDateTimeEnd(String strDate){
        return strDate+" 23:59:59";
    }

    public static String getDateTimeStr(){
//        DateFormat df = DateFormat.getDateTimeInstance();
//        return df.format(new Date());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        return sdf.format(new Date());
    }

    public static String getDateTimeStr(long lonTimeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        return sdf.format(lonTimeStamp);
    }

    public static String getDateStr() {
//        DateFormat df = DateFormat.getDateInstance();
//        return df.format(new Date());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        return sdf.format(new Date());
    }

    public static String getDateStr(long lonTimeStamp) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        return sdf.format(lonTimeStamp);
    }

    public static String getTimeStr() {
//        DateFormat df = DateFormat.getTimeInstance();
//        return df.format(new Date());
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss", Locale.CHINESE);
        return sdf.format(new Date());
    }

    public static String getTimeStr(long lonTimeStamp) {
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss", Locale.CHINESE);
        return sdf.format(lonTimeStamp);
    }


    /**=======================================
     * 作者：WangZezhi  (18-1-12  下午3:47)
     * 功能：保留两位小数
     * 描述：
     *=======================================*/
    public static String getCostStr(double dValue){
        return new BigDecimal(dValue).setScale(2, BigDecimal.ROUND_HALF_EVEN).toString()+"元";
    }

    /**=======================================
     * 作者：WangZezhi  (2018/6/4  17:23)
     * 功能：SnackBar
     * 描述：参数view是当前布局中随便一个view；最好是布局的根view
     *=======================================*/
    private static void snackbarShow(@NonNull View view,
                                     String strMsge,
                                     int iDuration,
                                     String strBtTitle,
                                     View.OnClickListener onClickListener){
        //构建snackbar
        Snackbar snackbar= Snackbar.make(view, strMsge, iDuration);
        //click的字体颜色
        snackbar.setActionTextColor(getColor(R.color.libroot_accent));
        //获得view
        View viewSnack=snackbar.getView();
        //设置背景
        viewSnack.setBackgroundColor(getColor(R.color.libroot_snackbar_bg));
        //内容字体颜色
        TextView tvSnackbarText = viewSnack.findViewById(R.id.snackbar_text);
        tvSnackbarText.setTextColor(Color.WHITE);
        //设置点击监听
        if(!TextUtils.isEmpty(strBtTitle) && onClickListener!=null){
            snackbar.setAction(strBtTitle, onClickListener);
        }
        //显示
        snackbar.show();
    }

    public static void snackbarShort(@NonNull View view,
                                     String strMsge,
                                     String strBtTitle,
                                     View.OnClickListener onClickListener){
        snackbarShow(view, strMsge, Snackbar.LENGTH_SHORT,
                strBtTitle, onClickListener);
    }
    public static void snackbarShort(@NonNull View view, String strMsge){
        snackbarShow(view, strMsge, Snackbar.LENGTH_SHORT,
                null, null);
    }


    public static void snackbarLong(@NonNull View view,
                                     String strMsge,
                                     String strBtTitle,
                                     View.OnClickListener onClickListener){
        snackbarShow(view, strMsge, Snackbar.LENGTH_LONG,
                strBtTitle, onClickListener);
    }
    public static void snackbarLong(@NonNull View view, String strMsge){
        snackbarShow(view, strMsge, Snackbar.LENGTH_LONG,
                null, null);
    }

    public static void snackbarIndefinite(@NonNull Activity activity, String strMsge){
        // 这种方式可以间接的获得当前activity的顶层容器
        View view=((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);
        //构建snackbar
        final Snackbar snackbar= Snackbar.make(view, strMsge, Snackbar.LENGTH_INDEFINITE);
        //click的字体颜色
        snackbar.setActionTextColor(getColor(R.color.libroot_accent));
        //获得view
        View viewSnack=snackbar.getView();
        //设置背景
        viewSnack.setBackgroundColor(getColor(R.color.libroot_snackbar_bg));
        //内容字体颜色
        TextView tvSnackbarText = viewSnack.findViewById(R.id.snackbar_text);
        tvSnackbarText.setTextColor(Color.WHITE);
        //设置点击监听
        snackbar.setAction(getString(R.string.libroot_dialog_esc), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        //显示
        snackbar.show();
    }

    /**
     * 功能:<br/>
     * &nbsp;&nbsp;&nbsp;用于在线程中显示Toast的界面，用于提示信息显示.<br/>
     *
     * @param context    (上下文).
     * @param strMessage (需要显示的消息).
     * @param iDuration  显示的等待时间,可选值:
     *                   {@link Toast#LENGTH_SHORT LENGTH_SHORT} 和
     *                   {@link Toast#LENGTH_LONG LENGTH_LONG}<br/>
     * @param //   显示Toast的风格，比如居中等 {@link Gravity#CENTER_HORIZONTAL CENTER_HORIZONTAL}
     * @see Toast
     */
    private static void toastInThreadShow(final Context context,
                                         final String strMessage,
                                          final int iDuration) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //自定义toast布局
                View layout = LayoutInflater.from(UtRoot.getAppContext())
                        .inflate(R.layout.libroot_toast, null);
                TextView tvLibRootToast = layout.findViewById(R.id.tvLibRootToast);
                //设置显示文本
                tvLibRootToast.setText(strMessage);
                //构建toast
                Toast toast = Toast.makeText(context, strMessage, iDuration);
                toast.setView(layout);
                toast.show();
            }
        });
    }

    public static void toastShort(final String strMsg) {
        toastInThreadShow(context, strMsg, Toast.LENGTH_SHORT);
    }
    public static void toastShort(@StringRes int intStrId) {
        String strMsg=getString(intStrId);
        toastShort(strMsg);
    }

    public static void toastLong(final String strMsg) {
        toastInThreadShow(context, strMsg, Toast.LENGTH_LONG);
    }
    public static void toastLong(@StringRes int intStrId) {
        String strMsg=getString(intStrId);
        toastLong(strMsg);
    }


}