package com.kmt.massage.utils.rx;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class AppSchedulerProvider implements SchedulerProvider {

    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

}
