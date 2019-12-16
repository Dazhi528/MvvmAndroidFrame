package com.dazhi.libroot.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import com.dazhi.libroot.R;
import com.dazhi.libroot.inte.InteCallRoot;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

/**
 * 功能：自定义带删除功能EditText
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/5/18 16:37
 * 修改日期：2018/5/18 16:37
 */
public class ViewClearEditText extends AppCompatEditText {
    private Context context;
    private boolean booFocus=false; //是否有焦点（默认没有焦点）
    private Drawable drawableClear; //删除按钮的引用
    private InteCallRoot inteRootCall; //单纯的点击清除事件回调
    private InteCallFocusChange inteCallFocusChange; //单纯的焦点改变事件回调
    private InteCallClearChange inteCallClearChange; //用于监听clear图标显示隐藏

    public ViewClearEditText(Context context) {
        this(context, null);
    }

    public ViewClearEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ViewClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        init();
    }

    private void init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        drawableClear = getCompoundDrawables()[2];
        if (drawableClear == null) {
            drawableClear = ContextCompat.getDrawable(getContext(), R.mipmap.ico_root_clear);
        }
        if(drawableClear==null){
            return;
        }
        drawableClear.setBounds(0, 0, drawableClear.getIntrinsicWidth(), drawableClear.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
//        //设置焦点改变监听
        setOnFocusChangeListener(onFocusChangeListener);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(textWatcher);
    }

    private OnFocusChangeListener onFocusChangeListener=new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(inteCallFocusChange!=null){
                inteCallFocusChange.onFocusChange(v, hasFocus);
            }
            //
            booFocus=hasFocus; //实时传值
            if (hasFocus) {
                Editable editable=getText();
                if(editable!=null){
                    setClearIconVisible(editable.length() > 0);
                }
            } else {
                setClearIconVisible(false);
            }
        }
    };

    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //只有在有焦点时候，才判断
            if(booFocus){
                setClearIconVisible(s.length() > 0);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     */
    private void setClearIconVisible(boolean visible) {
        if(inteCallClearChange!=null){
            inteCallClearChange.onClearChange(visible);
        }
        //
        Drawable drawableRight = visible ? drawableClear : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1],
                drawableRight,
                getCompoundDrawables()[3]);
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置在 EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && getCompoundDrawables()[2] != null) {
            boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                    && (event.getX() < ((getWidth() - getPaddingRight())));
            if (!touchable) {
                return super.onTouchEvent(event);
            }
            this.setText("");
            if(inteRootCall!=null){
                inteRootCall.call();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    /**=======================================
     * 作者：WangZezhi  (2018/10/22  10:46)
     * 功能：外置扩展部分
     * 描述：
     *=======================================*/
    public interface InteCallClearChange {
        void onClearChange(boolean booShow);
    }
    public void setClearChangeListener(InteCallClearChange inteCallClearChange) {
        this.inteCallClearChange=inteCallClearChange;
    }

    public interface InteCallFocusChange {
        void onFocusChange(View v, boolean hasFocus);
    }

    public void setFocusChangeListener(InteCallFocusChange inteCallFocusChange) {
        this.inteCallFocusChange=inteCallFocusChange;
    }

    public void setClearListener(InteCallRoot inteRootCall) {
        this.inteRootCall=inteRootCall;
    }

    /**
     * 设置晃动动画
     * @param counts 1秒钟晃动多少下(默认5下)
     */
    public void setShakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        //
        this.setAnimation(translateAnimation);
    }


}