package com.kmt.massage.ui.login;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.kmt.massage.R;
import com.kmt.massage.TestComponentRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
public class OfferedDetailActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(ApplicationProvider.getApplicationContext());

    @SuppressWarnings("deprecation")
    public final IntentsTestRule<LoginActivity> main =
            new IntentsTestRule<>(LoginActivity.class, false, false);

    @Rule
    public TestRule chain = RuleChain.outerRule(component).around(main);

    @SuppressWarnings("EmptyMethod")
    @Before
    public void setup() {

    }

    @Test
    public void checkViewsDisplay() {
        main.launchActivity(LoginActivity.getStartIntent(component.getContext()));

        onView(withId(R.id.et_email))
                .check(matches(isDisplayed()));

        onView(withId(R.id.et_password))
                .check(matches(isDisplayed()));

        onView(withText(R.string.login))
                .check(matches(isDisplayed()));
    }
}