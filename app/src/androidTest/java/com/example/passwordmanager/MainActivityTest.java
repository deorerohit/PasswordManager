package com.example.passwordmanager;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;

@LargeTest
public class MainActivityTest {

    ActivityScenario<CheckMasterPasswordActivity> scenario;


    @Before
    public void setup(){
        scenario = ActivityScenario.launch(CheckMasterPasswordActivity.class);
        scenario.moveToState(Lifecycle.State.RESUMED);
    }

    @Test
    public void testAddCredentials(){

        String masterPassword = "111";
        onView(withId(R.id.enterMasterPassword_edittext)).perform(ViewActions.typeText(masterPassword));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signIn_button)).perform(click());

        onView(withId(R.id.floatingBtn)).perform(click());

        String title = "Integration Test";
        String username ="test123";
        String password ="test@124";

        onView(withId(R.id.title_edittext)).perform(ViewActions.typeText(title));
        onView(withId(R.id.username_edittext)).perform(ViewActions.typeText(username));
        onView(withId(R.id.password_edittext)).perform(ViewActions.typeText(password));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.save_btn)).perform(click());

        onView(withText(title)).check(matches(isDisplayed()));
        onView(withText(username)).check(matches(isDisplayed()));
        onView(withText(password)).check(matches(isDisplayed()));
    }
}
