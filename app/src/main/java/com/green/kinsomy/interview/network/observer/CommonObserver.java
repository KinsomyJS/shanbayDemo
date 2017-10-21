package com.green.kinsomy.interview.network.observer;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.green.kinsomy.interview.model.ResponseResult;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by kinsomy on 2017/10/20.
 * 封装通用网络请求回调
 */

public abstract class CommonObserver<T> implements Observer<ResponseResult<T>> {
    protected Context mContext;

    public CommonObserver(Context cxt) {
        this.mContext = cxt;
    }

    public CommonObserver() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();

    }

    @Override
    public void onNext(ResponseResult<T> tBaseEntity) {
        onRequestEnd();
        if (tBaseEntity.isSuccess() || tBaseEntity.noResult()) {
            try {
                onSuccees(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                onCodeError(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
//        Log.w(TAG, "onError: ", );这里可以打印错误信息
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccees(ResponseResult<T> t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    protected void onCodeError(ResponseResult<T> t) throws Exception {
//        Toast.makeText(mContext,t.getMsg(),Toast.LENGTH_SHORT).show();
    }

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    protected void onRequestStart() {
    }

    protected void onRequestEnd() {

    }



}
