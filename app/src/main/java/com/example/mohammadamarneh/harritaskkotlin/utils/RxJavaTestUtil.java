package com.example.mohammadamarneh.harritaskkotlin.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

public class RxJavaTestUtil {
    public static <T> T getValue(Single<T> single){
        final CountDownLatch latch = new CountDownLatch(1);
        final Object[] data = new Object[1];
        single.subscribe(new DisposableSingleObserver<T>() {
            @Override
            public void onSuccess(T o) {
                data[0] = o;
                latch.countDown();
            }

            @Override
            public void onError(Throwable e) {
                throw new RuntimeException(e);
            }
        });

        try {
            latch.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return (T)data[0];
    }
}
