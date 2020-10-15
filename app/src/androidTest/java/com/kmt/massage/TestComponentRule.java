package com.kmt.massage;

import android.content.Context;

import com.kmt.massage.data.DataManager;
import com.kmt.massage.di.component.TestComponent;
import com.kmt.massage.di.module.ApplicationTestModule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TestComponentRule implements TestRule {

    private TestComponent mTestComponent;
    private final Context mContext;

    public TestComponentRule(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public DataManager getDataManager() {
        return mTestComponent.getDataManager();
    }

    private void setupDaggerTestComponentInApplication() {
        MvpApp application = ((MvpApp) mContext.getApplicationContext());
        mTestComponent = DaggerTestComponent.builder()
                .applicationTestModule(new ApplicationTestModule(application))
                .build();
        application.setComponent(mTestComponent);
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    setupDaggerTestComponentInApplication();
                    base.evaluate();
                } finally {
                    mTestComponent = null;
                }
            }
        };
    }
}
