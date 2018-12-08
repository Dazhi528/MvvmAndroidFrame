package com.dazhi.libroot.rx;

import org.reactivestreams.Publisher;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 功能：Rx公共定制方法类
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/7 11:54
 * 修改日期：2018/4/7 11:54
 */
public class UtRx {

    /**
     * 作者：WangZezhi  (17-11-21  上午9:32)
     * 功能：  统一线程处理
     * 详情：
     *   transformer变压器/转换器
     */
    public static <T> FlowableTransformer<T, T> rxScheduler() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
