package com.dazhi.libroot.ui.recycler.decoration;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 功能：RecyclerView的通用布局间隔线
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/6/5 17:41
 * 修改日期：2018/6/5 17:41
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class DecorationSpaceGridLinear extends RecyclerView.ItemDecoration {
    @RecyclerView.Orientation
    private int orientation; //布局方向
    private int space; //间隔像素值
    private int spanCount; //跨度数（同GridLayoutManager里设置的跨度数）
    private boolean booEdge; //边缘是否放入间隔

    //LinearLayoutManager用本构造方法，线性时，即跨度为1
    public DecorationSpaceGridLinear(int space) {
        this(space, false);
    }

    public DecorationSpaceGridLinear(int space, boolean booEdge) {
        this(RecyclerView.VERTICAL, space, booEdge);
    }

    public DecorationSpaceGridLinear(@RecyclerView.Orientation int orientation,
                                     int space, boolean booEdge) {
        this(orientation, space, 1, booEdge);
    }

    /**
     * =======================================
     * 作者：WangZezhi  (2018/7/11  09:24)
     * 功能：GridLayoutManager用本构造方法
     * 描述：
     *
     * @param orientation 方向
     * @param space       间隔像素值
     * @param spanCount   跨度数(即单行或单列内显示的最大项数)
     * @param booEdge     边缘是否放入间隔
     *                    =======================================
     */
    public DecorationSpaceGridLinear(@RecyclerView.Orientation int orientation, int space,
                                     int spanCount, boolean booEdge) {
        this.orientation = orientation;
        this.space = space;
        this.spanCount = spanCount;
        this.booEdge = booEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int childPosition = parent.getChildAdapterPosition(view);
        int groupIndex = childPosition % spanCount;
        if (orientation == RecyclerView.VERTICAL) {
            // 默认 垂直方向
            // column * ((1f / spanCount) * space)
            outRect.left = groupIndex * space / spanCount;
            // space - (column + 1) * ((1f /    spanCount) * space)
            outRect.right = space - (groupIndex + 1) * space / spanCount;
            if (booEdge) {
                //边缘是放入间隔
//                outRect.left = space - column * space / spanCount; // space - column * ((1f / spanCount) * space)
//                outRect.right = (column + 1) * space / spanCount; // (column + 1) * ((1f / spanCount) * space)
                if (childPosition < spanCount) { // top edge
                    outRect.top = space;
                }
                outRect.bottom = space; // bottom edge
            } else {
                //边缘不放入间隔
                if (childPosition >= spanCount) {
                    outRect.top = space; // top edge
                }
            }
        } else  {
            // 水平方向
            outRect.top = groupIndex * space / spanCount;
            outRect.bottom = space - (groupIndex + 1) * space / spanCount;
            if (booEdge) {
                //边缘是放入间隔
                if (childPosition < spanCount) {
                    outRect.left = space;
                }
                outRect.right = space;
//                outRect.top = space - column * space / spanCount;
//                outRect.bottom = (column + 1) * space / spanCount;
            } else {
                //边缘不放入间隔
                if (childPosition >= spanCount) {
                    outRect.left = space;
                }
            }
        }
    }

}
