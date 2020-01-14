package com.dazhi.libroot.ui.recycler.manager;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 功能：为RecyclerView实现FlowLayout量身打造
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2020-01-09 09:14
 * 修改日期：2020-01-09 09:14
 */
@SuppressWarnings("WeakerAccess")
public class FlowLayoutManager extends RecyclerView.LayoutManager implements
        RecyclerView.SmoothScroller.ScrollVectorProvider {
    private static final int INVALID_OFFSET = Integer.MIN_VALUE;

    private int mVerticalSpace = 0;
    private int mHorizontalSpace = 0;

    private SparseIntArray mColumnCountOfRow;
    private SparseArray<Rect> mScrapRects;
    private SparseArray<LayoutParams> mScrapLps;

    private int mOffsetX;
    private int mOffsetY;
    private int mItemCount;
    private int mLeft, mTop, mRight, mBottom; // RecyclerView Padding l、t、r、b
    private int mWidth, mHeight; // RecyclerView w、h

    private int mTotalWidth;
    private int mTotalHeight;
    private int mScrollOffsetX;
    private int mScrollOffsetY;

    private int mPendingScrollPositionOffset = INVALID_OFFSET;

    @RecyclerView.Orientation
    private int mOrientation = RecyclerView.VERTICAL;

    private RecyclerView.Recycler mRecycler;
    private RecyclerView.State mState;


    public FlowLayoutManager() {
        this(RecyclerView.VERTICAL);
    }
    public FlowLayoutManager(@RecyclerView.Orientation int orientation) {
        this(orientation, 0, 0);
    }
    public FlowLayoutManager(@RecyclerView.Orientation int orientation, int verticalSpace, int horizontalSpace) {
        setOrientation(orientation);
        setSpace(verticalSpace, horizontalSpace);
    }
    @SuppressWarnings("unused")
    public FlowLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Properties properties = getProperties(context, attrs, defStyleAttr, defStyleRes);
        setOrientation(properties.orientation);
        setReverseLayout(properties.reverseLayout);
        setStackFromEnd(properties.stackFromEnd);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return true;
    }

    @Override
    public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) {
        mPendingScrollPositionOffset = INVALID_OFFSET;
        removeAllViews();
    }

    @Override
    public boolean isAutoMeasureEnabled() {
        return true;
    }

    public void setOrientation(@RecyclerView.Orientation int orientation) {
        if (orientation == mOrientation) {
            return;
        }
        this.mOrientation = orientation;
        requestLayout();
    }

    public void setSpace(int verticalSpace, int horizontalSpace) {
        if (verticalSpace == mVerticalSpace && horizontalSpace == mHorizontalSpace) {
            return;
        }
        this.mVerticalSpace = verticalSpace;
        this.mHorizontalSpace = horizontalSpace;
        requestLayout();
    }

    @SuppressWarnings("unused")
    public void setReverseLayout(boolean reverseLayout) {
    }

    @SuppressWarnings("unused")
    public void setStackFromEnd(boolean stackFromEnd) {
    }


    @Override
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) lp);
        } else {
            return new LayoutParams(lp);
        }
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
        return lp instanceof LayoutParams;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        mRecycler = recycler;
        mState = state;
        mItemCount = getItemCount();
        if (mItemCount == 0) {
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()) {
            return;
        }
        mScrapRects = new SparseArray<>(mItemCount);
        mScrapLps = new SparseArray<>(mItemCount);
        mColumnCountOfRow = new SparseIntArray();
        mScrollOffsetX = 0;
        mScrollOffsetY = 0;
        mWidth = getWidth();
        mHeight = getHeight();
        mLeft = getPaddingLeft();
        mTop = getPaddingTop();
        mRight = getPaddingRight();
        mBottom = getPaddingBottom();
        mOffsetX = mLeft;
        mOffsetY = mTop;
        // 在布局之前，将所有的子View先Detach掉，放入到Scrap缓存中
        detachAndScrapAttachedViews(recycler);
        if (canScrollVertically()) {
            mTotalHeight = computeVerticalChildren(recycler);
            scrollVerticallyBy(mPendingScrollPositionOffset, recycler, state);
        } else {
            mTotalWidth = computeHorizontalChildren(recycler);
            scrollHorizontallyBy(mPendingScrollPositionOffset, recycler, state);
        }
        // fillAndRecycleView(recycler, state);
    }

    private int computeVerticalChildren(RecyclerView.Recycler recycler) {
        int maxRowHeight = 0;
        int totalHeight = 0;
        final Point point = new Point();
        for (int position=0; position<mItemCount; position++) {
            View posView = recycler.getViewForPosition(position);
            addView(posView);
            measureChildWithMargins(posView, 0, 0);
            final int width = getDecoratedMeasurementHorizontal(posView);
            final int height = getDecoratedMeasurementVertical(posView);
            if (mOffsetX + width + mHorizontalSpace > mWidth - mRight) {
                mOffsetX = mLeft;
                mOffsetY += maxRowHeight + (position == 0 ? 0 : mVerticalSpace);
                maxRowHeight = 0;
                point.x = 0;
                point.y++;
            }
            maxRowHeight = Math.max(height, maxRowHeight);
            LayoutParams lp = (LayoutParams) posView.getLayoutParams();
            lp.column = point.x++;
            lp.row = point.y;
            if (lp.column != 0) {
                mOffsetX += mHorizontalSpace;
            }
            mScrapLps.put(position, lp);
            mColumnCountOfRow.put(lp.row, lp.column + 1);
            Rect frame = mScrapRects.get(position);
            if (frame == null) {
                frame = new Rect();
            }
            frame.set(mOffsetX, mOffsetY, mOffsetX = mOffsetX + width, mOffsetY + height);
            mScrapRects.put(position, frame);
            totalHeight = Math.max(totalHeight, mOffsetY + height);
            // 布局
            layoutDecoratedWithMargins(posView, frame.left, frame.top, frame.right, frame.bottom);
        }
        return Math.max(totalHeight - mTop, getVerticalSpace());
    }

    private int computeHorizontalChildren(RecyclerView.Recycler recycler) {
        int maxColumnWidth = 0;
        int totalWidth = 0;
        final Point point = new Point();
        for (int position=0; position<mItemCount; position++) {
            //Stream.iterate(0, index -> ++index).limit(mItemCount).forEach(position -> {
            View viewPos = recycler.getViewForPosition(position);
            addView(viewPos);
            measureChildWithMargins(viewPos, 0, 0);
            final int width = getDecoratedMeasurementHorizontal(viewPos);
            final int height = getDecoratedMeasurementVertical(viewPos);
            if (mOffsetY + height + mVerticalSpace > mHeight - mBottom) {
                mOffsetY = mTop;
                mOffsetX += maxColumnWidth + (position == 0 ? 0 : mHorizontalSpace);
                maxColumnWidth = 0;
                point.x++;
                point.y = 0;
            }
            maxColumnWidth = Math.max(width, maxColumnWidth);
            LayoutParams lp = (LayoutParams) viewPos.getLayoutParams();
            lp.column = point.x;
            lp.row = point.y++;
            if (lp.row != 0) {
                mOffsetY += mVerticalSpace;
            }
            mScrapLps.put(position, lp);
            mColumnCountOfRow.put(lp.row, lp.column + 1);
            Rect frame = mScrapRects.get(position);
            if (frame == null) {
                frame = new Rect();
            }
            frame.set(mOffsetX, mOffsetY, mOffsetX + width, mOffsetY = mOffsetY + height);
            mScrapRects.put(position, frame);
            totalWidth = Math.max(totalWidth, mOffsetX + width);
            // 布局
            layoutDecoratedWithMargins(viewPos, frame.left, frame.top, frame.right, frame.bottom);
        }//);
        return Math.max(totalWidth - mLeft, getHorizontalSpace());
    }

    private int getDecoratedMeasurementVertical(View view) {
        final LayoutParams params = (LayoutParams) view.getLayoutParams();
        return getDecoratedMeasuredHeight(view) + params.topMargin + params.bottomMargin;
    }

    private int getDecoratedMeasurementHorizontal(View view) {
        final LayoutParams params = (LayoutParams) view.getLayoutParams();
        return getDecoratedMeasuredWidth(view) + params.leftMargin + params.rightMargin;
    }

    private int getVerticalSpace() {
        return mHeight - mTop - mBottom;
    }

    private int getHorizontalSpace() {
        return mWidth - mLeft - mRight;
    }

    @Override
    public void scrollToPosition(int position) {
        if (position >= getItemCount()) {
            return;
        }
        View view = findViewByPosition(position);
        if (view != null) {
            if (canScrollVertically()) {
                scrollVerticallyBy((int) (view.getY() - mTop), mRecycler, mState);
            } else if (canScrollHorizontally()) {
                scrollHorizontallyBy((int) (view.getX() - mLeft), mRecycler, mState);
            }
        }
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller scroller = new LinearSmoothScroller(recyclerView.getContext());
        scroller.setTargetPosition(position);
        startSmoothScroll(scroller);
    }

    @Override
    public PointF computeScrollVectorForPosition(int targetPosition) {
        final int direction = getChildCount()==0 || targetPosition<0 ? -1 : 1;
        PointF mPointF = new PointF();
        if (canScrollHorizontally()) {
            mPointF.x = direction;
            mPointF.y = 0;
        } else if (canScrollVertically()) {
            mPointF.x = 0;
            mPointF.y = direction;
        }
        return mPointF;
    }

    @Override
    public boolean canScrollVertically() {
        return mOrientation == RecyclerView.VERTICAL;
    }

    @Override
    public boolean canScrollHorizontally() {
        return mOrientation == RecyclerView.HORIZONTAL;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // distance y 即：Y轴滚动距离
        if (dy == 0 || mItemCount == 0) {
            return 0;
        }
        int travel = dy; // 旅行:travel
        if (mScrollOffsetY + travel < 0) {
            travel = -mScrollOffsetY;
        } else if (mScrollOffsetY + travel > mTotalHeight - getVerticalSpace()) {//如果滑动到最底部
            travel = mTotalHeight - getVerticalSpace() - mScrollOffsetY;
        }
        mPendingScrollPositionOffset = (mScrollOffsetY += travel);
        // detachAndScrapAttachedViews(recycler);
        offsetChildrenVertical(-travel);
        // fillAndRecycleView(recycler, state);
        return travel;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // distance x 即：X轴滚动距离
        if (dx == 0 || mItemCount == 0) {
            return 0;
        }
        int travel = dx;
        if (mScrollOffsetX + travel < 0) {
            travel = -mScrollOffsetX;
        } else if (mScrollOffsetX + travel > mTotalWidth - getHorizontalSpace()) {//如果滑动到最底部
            travel = mTotalWidth - getHorizontalSpace() - mScrollOffsetX;
        }
        mPendingScrollPositionOffset = (mScrollOffsetX += travel);
        // detachAndScrapAttachedViews(recycler);
        offsetChildrenHorizontal(-travel);
        // fillAndRecycleView(recycler, state);
        return travel;
    }


    /*=======================================
     * 作者：WangZezhi  (2020-01-13  10:53)
     * 功能：LayoutParams
     * 描述：
     *=======================================*/
    public static class LayoutParams extends RecyclerView.LayoutParams {
        //Current row in the grid
        public int row;
        //Current column in the grid
        public int column;


        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        @SuppressWarnings("unused")
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        @SuppressWarnings("unused")
        public LayoutParams(RecyclerView.LayoutParams source) {
            super(source);
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public String toString() {
            return "LayoutParams = {"
                    + "width=" + width
                    + ",height=" + height
                    + ",row=" + row
                    + ",column=" + column + "}";
        }
    }

}


//    @SuppressWarnings("unused")
//    @Orientation
//    public int getOrientation() {
//        return mOrientation;
//    }
//    @Nullable
//    public LayoutParams getLayoutParamsByPosition(int position) {
//        return mScrapLps.get(position);
//    }
//
//    @SuppressWarnings("unused")
//    public int getRow(int position) {
//        LayoutParams params = getLayoutParamsByPosition(position);
//        return params != null ? params.row : 0;
//    }
//
//    @SuppressWarnings("unused")
//    public int getColumn(int position) {
//        LayoutParams params = getLayoutParamsByPosition(position);
//        return params != null ? params.column : 0;
//    }
//
//    @SuppressWarnings("unused")
//    public int getColumnCountOfRow(int row) {
//        return mColumnCountOfRow.get(row, 1);
//    }



//    @SuppressWarnings("unused")
//    private void fillAndRecycleView(RecyclerView.Recycler recycler, RecyclerView.State state) {
//        if (mItemCount == 0 || state.isPreLayout()) {
//            return;
//        }
//        Rect displayFrame = canScrollVertically()
//                ? new Rect(mLeft, mScrollOffsetY, mWidth, mScrollOffsetY + mHeight)
//                : new Rect(mScrollOffsetX, mTop, mScrollOffsetX + mWidth, mHeight);
//        for (int index=0; index<mItemCount; index++) {
//            //Stream.iterate(0, index -> ++index).limit(mItemCount).forEach(index -> {
//            Rect rect = mScrapRects.get(index);
//            if (!Rect.intersects(displayFrame, rect)) {
//                View scrap = getChildAt(index);
//                if (scrap != null) {
//                    removeAndRecycleView(scrap, recycler);
//                }
//                return;
//            }
//            View scrap = recycler.getViewForPosition(index);
//            addView(scrap);
//            measureChildWithMargins(scrap, 0, 0);
//            if (canScrollVertically()) {
//                layoutDecoratedWithMargins(scrap, rect.left, rect.top - mScrollOffsetY, rect.right, rect.bottom - mScrollOffsetY);
//            } else {
//                layoutDecoratedWithMargins(scrap, rect.left - mScrollOffsetX, rect.top, rect.right - mScrollOffsetX, rect.bottom);
//            }
//        }//);
//    }
