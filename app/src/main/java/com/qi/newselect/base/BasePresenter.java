package com.qi.newselect.base;

import java.lang.ref.WeakReference;

/**
 * Created by dongqi on 2016/8/9.
 */
public abstract class BasePresenter<T> {
    //弱引用
    WeakReference<T> reference ;
//    rx.subscriptions.CompositeSubscription subscription;
    public void attchView(T t){
        reference = new WeakReference<T>(t);

    }
    protected T getIView(){
        return reference.get();
    }

    public boolean isIViewAttched(){
        return reference!=null&&reference.get()!=null;
    }

//    public void addSubscription(rx.Subscription s){
//        if (this.subscription == null){
//            this.subscription =  new CompositeSubscription();
//        }
//        this.subscription.add(s);
//    }
//
//    public void unSubscription(){
//        if (this.subscription != null){
//            this.subscription.unsubscribe();
//        }
//    }
}
