package com.kmt.massage.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.StringRes;

@SuppressWarnings({"unused", "RedundantSuppression"})
public abstract class BaseSubView extends ViewGroup implements SubMvpView {

    private MvpView mParentMvpView;

    public BaseSubView(Context context) {
        super(context);
    }

    public BaseSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public BaseSubView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void attachParentMvpView(MvpView mvpView) {
        mParentMvpView = mvpView;
    }

    @Override
    public void showMessage(String message) {
        if (mParentMvpView != null) {
            mParentMvpView.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (mParentMvpView != null) {
            mParentMvpView.showMessage(resId);
        }
    }

    protected abstract void bindViewsAndSetOnClickListeners();

    protected abstract void setUp();
}
