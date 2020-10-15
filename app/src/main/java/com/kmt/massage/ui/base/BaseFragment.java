package com.kmt.massage.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.kmt.massage.di.component.ActivityComponent;

import butterknife.Unbinder;

@SuppressWarnings({"unused", "RedundantSuppression", "EmptyMethod"})
public abstract class BaseFragment extends Fragment implements MvpView {

    private BaseActivity mActivity;
    private Unbinder mUnBinder;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void showMessage(String message) {
        if (mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public ActivityComponent getActivityComponent() {
        if (mActivity != null) {
            return mActivity.getActivityComponent();
        }
        return null;
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    protected abstract void setUp(View view);

    @Override
    public void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
}
