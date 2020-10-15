package com.kmt.massage.ui.main.musicDialog;
import com.kmt.massage.ui.base.MvpPresenter;

public interface MusicListMvpPresenter<V extends MusicListMvpView> extends MvpPresenter<V> {

    void onCancelClick();

}
