package com.kmt.massage.ui.main;

import com.kmt.massage.di.PerActivity;
import com.kmt.massage.ui.base.MvpPresenter;

@SuppressWarnings({"unused", "RedundantSuppression", "EmptyMethod"})
@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    void onViewInitialized();

}
