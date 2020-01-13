package com.dazhi.libroot.ui.recycler.decoration;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
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
public class DecorationSpaceGridLinear extends RecyclerView.ItemDecoration {
    private boolean booEdge; //边缘是否放入间隔
    private int space; //间隔像素值
    private int spanCount; //跨度数（同GridLayoutManager里设置的跨度数）

    //LinearLayoutManager用本构造方法
    public DecorationSpaceGridLinear(boolean booEdge, int space) {
        //线性用不到跨度数这个值，为了防止网格误用本构造，给他设置个默认值为3
        this(booEdge, space, 3);
    }

    /**=======================================
     * 作者：WangZezhi  (2018/7/11  09:24)
     * 功能：GridLayoutManager用本构造方法
     * 描述：
     * @param booEdge   边缘是否放入间隔
     * @param space     间隔像素值
     * @param spanCount 跨度数(即单行或单列内显示的最大项数)
     * =======================================*/
    public DecorationSpaceGridLinear(boolean booEdge, int space, int spanCount) {
        this.booEdge = booEdge;
        this.space = space;
        this.spanCount=spanCount;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
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
                outRect.left = space - column * space / spanCount; // space - column * ((1f / spanCount) * space)
                outRect.right = (column + 1) * space / spanCount; // (column + 1) * ((1f / spanCount) * space)
                if (childPosition < spanCount) { // top edge
                    outRect.top = space;
                }
                outRect.bottom = space; // bottom edge
            } else {
                //边缘不放入间隔
                outRect.left = column * space / spanCount; // column * ((1f / spanCount) * space)
                outRect.right = space - (column + 1) * space / spanCount; // space - (column + 1) * ((1f /    spanCount) * space)
                if (childPosition >= spanCount) {
                    outRect.top = space; // top edge
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
                    outRect.left = space;
                }
                outRect.right = space;
                outRect.top = space - column * space / spanCount;
                outRect.bottom = (column + 1) * space / spanCount;
            } else {
                //边缘不放入间隔
                if (childPosition >= spanCount) {
                    outRect.left = space;
                }
                outRect.top = column * space / spanCount;
                outRect.bottom = space - (column + 1) * space / spanCount;
            }
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
                    outRect.top = space;
                } else {
                    outRect.top = 0;
                }
                outRect.bottom = space;
                outRect.left = space;
                outRect.right = space;
            }else {
                //边缘不放入间隔
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = 0;
                } else {
                    outRect.top = space;
                }
            }
            return;
        }
        //水平方向
        if(intOrientation== LinearLayoutManager.HORIZONTAL){
            if(booEdge){
                //边缘是放入间隔
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.left = space;
                } else {
                    outRect.left = 0;
                }
                outRect.top = space;
                outRect.bottom = space;
                outRect.right = space;
            }else {
                //边缘不放入间隔
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.left = 0;
                } else {
                    outRect.left = space;
                }
            }
        }
    }

}
