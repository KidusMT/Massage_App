package com.kmt.massage.ui.main;

import com.kmt.massage.data.DataManager;
import com.kmt.massage.ui.base.BasePresenter;
import com.kmt.massage.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    @SuppressWarnings({"unused", "RedundantSuppression"})
    private static final String TAG = MainPresenter.class.getSimpleName();

    @Inject
    public MainPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    @Override
    public void onViewInitialized() {

    }
}
