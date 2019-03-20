package com.dazhi.libroot.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.dazhi.libroot.R;
import com.dazhi.libroot.util.UtRoot;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * 功能：下拉刷新
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/6/13 14:10
 * 修改日期：2018/6/13 14:10
 */
public class HeaderRefresh extends ClassicsHeader {

    public HeaderRefresh(Context context) {
        super(context);
        initStaticVarHeader();
    }

    public HeaderRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        initStaticVarHeader();
    }

    public HeaderRefresh(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStaticVarHeader();
    }

    @RequiresApi(21)
    public HeaderRefresh(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initStaticVarHeader();
    }


    private void initStaticVarHeader() {
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = UtRoot.getString(R.string.REFRESH_HEADER_PULLDOWN);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = UtRoot.getString(R.string.REFRESH_HEADER_REFRESHING);
        ClassicsHeader.REFRESH_HEADER_LOADING = UtRoot.getString(R.string.REFRESH_HEADER_LOADING);
        ClassicsHeader.REFRESH_HEADER_RELEASE = UtRoot.getString(R.string.REFRESH_HEADER_RELEASE);
        ClassicsHeader.REFRESH_HEADER_FINISH = UtRoot.getString(R.string.REFRESH_HEADER_FINISH);
        ClassicsHeader.REFRESH_HEADER_FAILED = UtRoot.getString(R.string.REFRESH_HEADER_FAILED);
        ClassicsHeader.REFRESH_HEADER_LASTTIME = UtRoot.getString(R.string.REFRESH_HEADER_LASTTIME);
        ClassicsHeader.REFRESH_HEADER_SECOND_FLOOR = UtRoot.getString(R.string.REFRESH_HEADER_SECOND_FLOOR);
    }


}
