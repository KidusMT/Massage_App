package com.kmt.massage.utils.rx;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;


@SuppressWarnings({"unused", "RedundantSuppression"})
public class TestSchedulerProvider implements SchedulerProvider {

    private final TestScheduler mTestScheduler;

    public TestSchedulerProvider(TestScheduler testScheduler) {
        this.mTestScheduler = testScheduler;
    }

    @Override
    public Scheduler ui() {
        return mTestScheduler;
    }

    @Override
    public Scheduler computation() {
        return mTestScheduler;
    }

    @Override
    public Scheduler io() {
        return mTestScheduler;
    }

}
