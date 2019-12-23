package com.dazhi.libroot.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.dazhi.libroot.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * 功能：可拖动的浮动按钮
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2019-12-23 16:05
 * 修改日期：2019-12-23 16:05
 */
public class ViewFloatingDragButton extends FloatingActionButton {
    private boolean booDragEnable = true; // 默认可拖动
    //
    private int mParentWidth; // 父容器的宽度
    private int mParentHeight;// 父容器的高度
    private float mX;
    private float mY;
    private int mDragLeft;
    private int mDragTop;
    private int mDragRight;
    private int mDragBottom;
    private boolean hadDrag = false; // 是否曾经有过移动
    private boolean booDrag = false; // 用户是否拖动


    public ViewFloatingDragButton(Context context) {
        this(context, null);
    }

    public ViewFloatingDragButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewFloatingDragButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewFloatingDragButton);
        booDragEnable = typedArray.getBoolean(R.styleable.ViewFloatingDragButton_dragEnable, true);
        typedArray.recycle();
    }

    /*=======================================
     * 作者：WangZezhi  (2019-12-23  16:21)
     * 功能：get/set
     * 描述：
     *=======================================*/
    public boolean getBooDragEnable() {
        return booDragEnable;
    }

    public void setBooDragEnable(boolean booDragEnable) {
        this.booDragEnable = booDragEnable;
    }

    /*=======================================
     * 作者：WangZezhi  (2019-12-23  16:22)
     * 功能：核心业务逻辑部分
     * 描述：
     *=======================================*/
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 防止布局重置时重置此View的位置
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) getParent()).addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    // 如果此view已经移动过了，说明不是布局的位置，需要改为移动后的位置
                    if (hadDrag && mDragRight != 0 && mDragBottom != 0)
                        layout(mDragLeft, mDragTop, mDragRight, mDragBottom);
                }
            });
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (getParent() instanceof ViewGroup) {
            mParentWidth = ((ViewGroup) getParent()).getWidth();
            mParentHeight = ((ViewGroup) getParent()).getHeight();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!booDragEnable) {
            return super.onTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = ev.getX();
                mY = ev.getY();
                super.onTouchEvent(ev);
                return true;
            case MotionEvent.ACTION_MOVE:
                int scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                float x = ev.getX();
                float y = ev.getY();
                x = x - mX;
                y = y - mY;
                if (Math.abs(scaledTouchSlop) < Math.abs(x) || Math.abs(scaledTouchSlop) < Math.abs(y)) {
                    booDrag = true;
                }
                if (booDrag) {
                    mDragLeft = (int) (getX() + x);
                    mDragTop = (int) (getY() + y);
                    mDragRight = (int) (getX() + getWidth() + x);
                    mDragBottom = (int) (getY() + getHeight() + y);
                    //防止滑出父界面
                    if (mDragLeft < 0 || mDragRight > mParentWidth) {
                        mDragLeft = (int) getX();
                        mDragRight = (int) getX() + getWidth();
                    }
                    if (mDragTop < 0 || mDragBottom > mParentHeight) {
                        mDragTop = (int) getY();
                        mDragBottom = (int) getY() + getHeight();
                    }
                    layout(mDragLeft, mDragTop, mDragRight, mDragBottom);
                    hadDrag = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (booDrag) {
                    booDrag = false;
                    setPressed(false); //重置点击状态
                    return true;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

}
