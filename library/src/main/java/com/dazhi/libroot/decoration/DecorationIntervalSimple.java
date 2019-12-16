package com.dazhi.libroot.decoration;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 功能：主页面网格布局间隔定制
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/7/11 10:46
 * 修改日期：2018/7/11 10:46
 */
public class DecorationIntervalSimple extends RecyclerView.ItemDecoration {
    private int interval; //间隔（单位px）

    public DecorationIntervalSimple(int interval){
        this.interval=interval;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(interval, interval, interval, interval);
    }

}
