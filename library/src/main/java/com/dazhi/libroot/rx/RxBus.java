package com.dazhi.libroot.rx;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * 功能：自定义的rx总线
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/19 14:55
 * 修改日期：2018/4/19 14:55
 */
public class RxBus {
    private FlowableProcessor flowableProcessor;

    private RxBus(){
        //PublishSubject只会把在订阅发生的时间点之后来自原始Flowable的数据发射给观察者
        flowableProcessor = PublishProcessor.create().toSerialized();
    }
    private static final class classHolder {
        static final RxBus INSTANCE = new RxBus();
    }
    public static RxBus self() {
        return classHolder.INSTANCE;
    }

    /**==========================================
     * 作者：WangZezhi  (17-12-14  上午11:21)
     * 功能：           方法区
     * 详情：用法
     * 发送 RxBus.self().post(new BnScanSearch(sCarNum));
     *
     * 接收
     * rxAdd(RxBus.self().getFlowable(BnScanSearch::class.java)
     * .compose(UtRx.rxScheduler())
     * .subscribeWith(object : SubscriberCustom<BnScanSearch>(mvpView) {
     * override fun next(bn: BnScanSearch) {
     * val et=greenDaoHelper.getIntoRecordByCarNum(bn.sCarNum)
     * if(et==null){
     * mvpView.updatePhotoSearch(bn.sCarNum)
     * }else{
     * mvpView.jumpActivityByEt(et)
     *  }
     *  }
     *  override fun onError(t: Throwable?) {
     *  mvpView.alertDialogShow("拍照识别车牌识别，请重试或手动输入搜索")
     *  }
     *  })
     *  )
     *
     *  或
     *  disposable=RxBus.instance.getDisposable(BnDialogotUpOne::class.java,
     *  Consumer {
     *  iNoUpRecordLocal--
     *  if(iNoUpRecordLocal<=0){
     *  iNoUpRecordLocal=0
     *  btDialogLogotUp.visibility=View.GONE
     *  }
     *  updateText()
     *  ToastUtils.showShort("成功上传一条记录")
     *  })
     * =========================================*/
    // 提供了一个新的事件(放入要发送的自定义bn对象)
    public void post(@NonNull Object any) {
        flowableProcessor.onNext(any);
    }

    // 根据传递的 eventType 返回特定类型(eventType)的 被观察者;  組成、構成、构成（compose）
    public <T> Flowable<T> getFlowable(Class<T> eventType) {
        return flowableProcessor.ofType(eventType);
    }
    public <T> Disposable getDisposable(Class<T> eventType, Consumer<T> consumer) {
        return flowableProcessor.ofType(eventType).compose(rxScheduler()).subscribe(consumer);
    }

    public void unregisterAll() {
        //会将所有由flowableProcessor生成的Observable都置completed状态,后续的所有消息都收不到了
        flowableProcessor.onComplete();
    }

    /**
     * 作者：WangZezhi  (17-11-21  上午9:32)
     * 功能：  统一线程处理
     * 详情：
     *   transformer变压器/转换器
     */
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
