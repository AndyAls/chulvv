package com.qlckh.chunlvv.http.interfaces;

import com.qlckh.chunlvv.http.bean.BaseData;

import io.reactivex.disposables.Disposable;

/**
 * @author Andy
 * @date   2018/5/15 18:36
 * Desc:    IDataSubscriber.java
 */
public interface IDataSubscriber<T> {

    /**
     * doOnSubscribe 回调
     *
     * @param d Disposable
     */
    void doOnSubscribe(Disposable d);

    /**
     * 错误回调
     *
     * @param errorMsg 错误信息
     */
    void doOnError(String errorMsg);

    /**
     * 成功回调
     *
     * @param baseData 基础泛型
     */
    void doOnNext(BaseData<T> baseData);

    /**
     * 请求完成回调
     */
    void doOnCompleted();
}
