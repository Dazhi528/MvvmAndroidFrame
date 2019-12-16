package com.dazhi.libroot.decoration;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 功能：RecyclerView的GridLayoutManager布局间隔线
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/6/5 17:41
 * 修改日期：2018/6/5 17:41
 * GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, true)
 * 表示排成3列，每行最多放3项，true表示从右到左排列
 * //
 * GridLayoutManager(this, 4, GridLayoutManager.HORIZONTAL, false)
 * 表示排成4行，每列最多放4项，false表示从左到右排列
 */
public class DecorationIntervalGridLinear extends RecyclerView.ItemDecoration {
    private boolean booEdge; //边缘是否放入间隔
    private int interval; //间隔像素值
    private int spanCount; //跨度数（同GridLayoutManager里设置的跨度数）

    //LinearLayoutManager用本构造方法
    public DecorationIntervalGridLinear(boolean booEdge, int interval) {
        //线性用不到跨度数这个值，为了防止网格误用本构造，给他设置个默认值为3
        this(booEdge, interval, 3);
    }

    /**=======================================
     * 作者：WangZezhi  (2018/7/11  09:24)
     * 功能：GridLayoutManager用本构造方法
     * 描述：
     * @param booEdge   边缘是否放入间隔
     * @param interval     间隔像素值
     * @param spanCount 跨度数(即单行或单列内显示的最大项数)
     * =======================================*/
    public DecorationIntervalGridLinear(boolean booEdge, int interval, int spanCount) {
        this.booEdge = booEdge;
        this.interval = interval;
        this.spanCount=spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //Manager
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager == null) {
            return;
        }
        if (manager instanceof GridLayoutManager) {
            handleGridLayoutManager(outRect, view, parent, (GridLayoutManager) manager);
            return;
        }
        if (manager instanceof LinearLayoutManager) {
            handleLinearLayoutManager(outRect, view, parent, (LinearLayoutManager) manager);
            return;
        }
    }

    //处理网格布局
    private void handleGridLayoutManager(Rect outRect, View view, RecyclerView parent, GridLayoutManager gridLayoutManager){
        // int allItemCount = parent.getAdapter().getItemCount(); //All Item Count
        int intOrientation=gridLayoutManager.getOrientation(); //布局方向
        //默认 垂直方向
        if(intOrientation== GridLayoutManager.VERTICAL){
            int childPosition = parent.getChildAdapterPosition(view);
            int column = childPosition % spanCount;
            if (booEdge) {
                //边缘是放入间隔
                outRect.left = interval - column * interval / spanCount; // interval - column * ((1f / spanCount) * interval)
                outRect.right = (column + 1) * interval / spanCount; // (column + 1) * ((1f / spanCount) * interval)
                if (childPosition < spanCount) { // top edge
                    outRect.top = interval;
                }
                outRect.bottom = interval; // bottom edge
            } else {
                //边缘不放入间隔
                outRect.left = column * interval / spanCount; // column * ((1f / spanCount) * interval)
                outRect.right = interval - (column + 1) * interval / spanCount; // interval - (column + 1) * ((1f /    spanCount) * interval)
                if (childPosition >= spanCount) {
                    outRect.top = interval; // top edge
                }
            }
            return;
        }
        //水平方向
        if(intOrientation== GridLayoutManager.HORIZONTAL){
            //子项在整个布局内的位置
            int childPosition = parent.getChildAdapterPosition(view);
            //子项在单列中的位置
            int column = childPosition % spanCount;
            if (booEdge) {
                //边缘是放入间隔
                if (childPosition < spanCount) {
                    outRect.left = interval;
                }
                outRect.right = interval;
                outRect.top = interval - column * interval / spanCount;
                outRect.bottom = (column + 1) * interval / spanCount;
            } else {
                //边缘不放入间隔
                if (childPosition >= spanCount) {
                    outRect.left = interval;
                }
                outRect.top = column * interval / spanCount;
                outRect.bottom = interval - (column + 1) * interval / spanCount;
            }
            return;
        }
    }

    //处理线性布局
    private void handleLinearLayoutManager(Rect outRect, View view, RecyclerView parent, LinearLayoutManager linearLayoutManager){
        int intOrientation=linearLayoutManager.getOrientation(); //布局方向
        //默认 垂直方向
        if(intOrientation== LinearLayoutManager.VERTICAL){
            if(booEdge){
                //边缘是放入间隔
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = interval;
                } else {
                    outRect.top = 0;
                }
                outRect.bottom = interval;
                outRect.left = interval;
                outRect.right = interval;
            }else {
                //边缘不放入间隔
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = 0;
                } else {
                    outRect.top = interval;
                }
            }
            return;
        }
        //水平方向
        if(intOrientation== LinearLayoutManager.HORIZONTAL){
            if(booEdge){
                //边缘是放入间隔
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.left = interval;
                } else {
                    outRect.left = 0;
                }
                outRect.top = interval;
                outRect.bottom = interval;
                outRect.right = interval;
            }else {
                //边缘不放入间隔
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.left = 0;
                } else {
                    outRect.left = interval;
                }
            }
            return;
        }
    }


}
