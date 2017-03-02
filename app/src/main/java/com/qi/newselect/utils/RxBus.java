package com.qi.newselect.utils;

import android.os.Bundle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by dongqi on 2017/1/13.
 */

public class RxBus {

    private static RxBus defInstance;
    private final Subject<Object,Object> bus;
    /**
     * ConcurrentHashMap是一个线程安全的HashMap，
     * 采用stripping lock（分离锁），效率比HashTable高很多。
     */
    private final Map<Class<?>,Object> mStickyEventMap ;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者

    /**
     *
     */
    private RxBus() {
        mStickyEventMap = new ConcurrentHashMap<>();
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    /**
     * 1、Subject同时充当了Observer和Observable的角色，Subject是非线程安全的，
     * 要避免该问题，需要将 Subject转换为一个 SerializedSubject ，
     * 上述RxBus类中把线程非安全的PublishSubject包装成线程安全的Subject。

     2、PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者。

     3、ofType操作符只发射指定类型的数据，其内部就是filter+cast
     * @return
     */
    public static RxBus getInstance() {
        if (defInstance == null) {
            synchronized (RxBus.class) {
                if (defInstance == null) {
                    defInstance = new RxBus();
                }
            }
        }
        return defInstance;
    }

    /**
     * 发送一个新的事件
     * @param obj
     */
    public void post(Object obj) {
        bus.onNext(obj);
    }

    /**
     * 接收普通事件
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     * ofType = filter + cast
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }


    /**
     * 发送粘性事件
     * @param event
     */
    public void postSticky(Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.getClass(), event);
        }
        post(event);
    }

    /**
     * 接收粘性事件
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservableSticky(final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            Observable<T> obs = bus.ofType(eventType);
            final Object obj = mStickyEventMap.get(eventType);
            if (obj != null) {
                return obs.mergeWith(Observable.create(new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        subscriber.onNext(eventType.cast(obj));
                    }
                }));
            } else {
                return obs;
            }
        }
    }


}
