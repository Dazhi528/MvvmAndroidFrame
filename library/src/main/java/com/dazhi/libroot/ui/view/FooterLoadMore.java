package com.dazhi.libroot.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import com.dazhi.libroot.R;
import com.dazhi.libroot.util.UtRoot;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

/**
 * 功能：上拉加载更多
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/6/13 14:02
 * 修改日期：2018/6/13 14:02
 */
public class FooterLoadMore extends ClassicsFooter {

    public FooterLoadMore(Context context) {
        super(context);
        initStaticVarFooter();
    }

    public FooterLoadMore(Context context, AttributeSet attrs) {
        super(context, attrs);
        initStaticVarFooter();
    }

    public FooterLoadMore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStaticVarFooter();
    }


    private void initStaticVarFooter() {
        ClassicsFooter.REFRESH_FOOTER_PULLUP = UtRoot.getString(R.string.REFRESH_FOOTER_PULLUP);
        ClassicsFooter.REFRESH_FOOTER_RELEASE = UtRoot.getString(R.string.REFRESH_FOOTER_RELEASE);
        ClassicsFooter.REFRESH_FOOTER_LOADING = UtRoot.getString(R.string.REFRESH_FOOTER_LOADING);
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = UtRoot.getString(R.string.REFRESH_FOOTER_FINISH);
        ClassicsFooter.REFRESH_FOOTER_FINISH = UtRoot.getString(R.string.REFRESH_FOOTER_FINISH);
        ClassicsFooter.REFRESH_FOOTER_FAILED = UtRoot.getString(R.string.REFRESH_FOOTER_FAILED);
        ClassicsFooter.REFRESH_FOOTER_ALLLOADED = UtRoot.getString(R.string.REFRESH_FOOTER_ALLLOADED);
    }


}
