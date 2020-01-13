package com.dazhi.libroot.ui.recycler.decoration;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 功能：主页面网格布局间隔定制
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/7/11 10:46
 * 修改日期：2018/7/11 10:46
 */
public class DecorationSpaceSimple extends RecyclerView.ItemDecoration {
    private int space; //间隔（单位px）

    public DecorationSpaceSimple(int space){
        this.space=space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(space, space, space, space);
    }

}
