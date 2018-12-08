package com.dazhi.libroot.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.dazhi.libroot.R;

/**
 * 功能：自定义带图片的EditText
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/3/24 11:16
 * 修改日期：2018/3/24 11:16
 * 传入dp值
 */
public class ViewDrawableEdit extends AppCompatEditText {
    private Context context;
    //左
    private Drawable drawableLeft;
    private int leftWidth;
    private int leftHeight;
    //顶
    private Drawable drawableTop;
    private int topWidth;
    private int topHeight;
    //右
    private Drawable drawableRight;
    private int rightWidth;
    private int rightHeight;
    //底
    private Drawable drawableBottom;
    private int bottomWidth;
    private int bottomHeight;

    /**=======================================
     * 作者：WangZezhi  (2018/7/5  11:08)
     * 功能：构造方法
     * 描述：
     *=======================================*/
    public ViewDrawableEdit(Context context){
        this(context, null);
    }
    public ViewDrawableEdit(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context=context;
        //
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.attrs_view_drawable);
        //顶
        drawableTop = typedArray.getDrawable(R.styleable.attrs_view_drawable_drawableTop);
        if (drawableTop != null) {
            topWidth = typedArray.getDimensionPixelOffset(R.styleable.attrs_view_drawable_drawableTopWidth, dp2px(context, 20));
            topHeight = typedArray.getDimensionPixelOffset(R.styleable.attrs_view_drawable_drawableTopHeight, dp2px(context, 20));
        }
        //底
        drawableBottom = typedArray.getDrawable(R.styleable.attrs_view_drawable_drawableBottom);
        if (drawableBottom != null) {
            bottomWidth = typedArray.getDimensionPixelOffset(R.styleable.attrs_view_drawable_drawableBottomWidth, dp2px(context, 20));
            bottomHeight = typedArray.getDimensionPixelOffset(R.styleable.attrs_view_drawable_drawableBottomHeight, dp2px(context, 20));
        }
        //左
        drawableLeft = typedArray.getDrawable(R.styleable.attrs_view_drawable_drawableLeft);
        if (drawableLeft != null) {
            leftWidth = typedArray.getDimensionPixelOffset(R.styleable.attrs_view_drawable_drawableLeftWidth, dp2px(context, 20));
            leftHeight = typedArray.getDimensionPixelOffset(R.styleable.attrs_view_drawable_drawableLeftHeight, dp2px(context, 20));
        }
        //右
        drawableRight = typedArray.getDrawable(R.styleable.attrs_view_drawable_drawableRight);
        if (drawableRight != null) {
            rightWidth = typedArray.getDimensionPixelOffset(R.styleable.attrs_view_drawable_drawableRightWidth, dp2px(context, 20));
            rightHeight = typedArray.getDimensionPixelOffset(R.styleable.attrs_view_drawable_drawableRightHeight, dp2px(context, 20));
        }
    }

    private int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (drawableLeft != null) {
            drawableLeft.setBounds(0, 0, leftWidth, leftHeight);
        }
        if (drawableTop != null) {
            drawableTop.setBounds(0, 0, topWidth, topHeight);
        }
        if (drawableRight != null) {
            drawableRight.setBounds(0, 0, rightWidth, rightHeight);
        }
        if (drawableBottom != null) {
            drawableBottom.setBounds(0, 0, bottomWidth, bottomHeight);
        }
    }


    /**=======================================
     * 作者：WangZezhi  (2018/7/5  10:58)
     * 功能：     代码设置图片区
     * 描述：
     *=======================================*/
    /**
     * 设置左侧图片并重绘
     */
    public void setDrawableLeft(Drawable drawableLeft) {
        this.drawableLeft = drawableLeft;
        invalidate();
    }
    public void setDrawableLeft(int drawableLeftRes) {
        this.drawableLeft = context.getResources().getDrawable(drawableLeftRes);
        invalidate();
    }
    /**
     * 设置顶部图片并重绘
     */
    public void setDrawableTop(Drawable drawableTop) {
        this.drawableTop = drawableTop;
        invalidate();
    }
    public void setDrawableTop(int drawableTopRes) {
        this.drawableTop = context.getResources().getDrawable(drawableTopRes);
        invalidate();
    }
    /**
     * 设置右侧图片并重绘
     */
    public void setDrawableRight(Drawable drawableRight) {
        this.drawableRight = drawableRight;
        invalidate();
    }
    public void setDrawableRight(int drawableRightRes) {
        this.drawableRight = context.getResources().getDrawable(drawableRightRes);
        invalidate();
    }
    /**
     * 设置底部图片并重绘
     */
    public void setDrawableBottom(Drawable drawableBottom) {
        this.drawableBottom = drawableBottom;
        invalidate();
    }
    public void setDrawableBottom(int drawableBottomRes) {
        this.drawableBottom = context.getResources().getDrawable(drawableBottomRes);
        invalidate();
    }


}
