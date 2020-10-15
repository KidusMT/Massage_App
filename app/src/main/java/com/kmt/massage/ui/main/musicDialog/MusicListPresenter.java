package com.kmt.massage.ui.main.musicDialog;

import com.kmt.massage.data.DataManager;
import com.kmt.massage.ui.base.BasePresenter;
import com.kmt.massage.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MusicListPresenter<V extends MusicListMvpView> extends BasePresenter<V>
        implements MusicListMvpPresenter<V> {

    public static final String TAG = MusicListPresenter.class.getSimpleName();

    @Inject
    public MusicListPresenter(DataManager dataManager,
                              SchedulerProvider schedulerProvider,
                              CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onCancelClick() {
        getMvpView().dismissDialog();
    }
}
