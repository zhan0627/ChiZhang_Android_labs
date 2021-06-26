package algonquin.cst2335.chizhangandroidlabs;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView(withId(R.id.pw));

        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());


        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }
    /**
     * @Test test the uppercase
     * */
    @Test
    public void testFindMissingUpperCase(){
        ViewInteraction appCompatEditText = onView( withId(R.id.pw));
        appCompatEditText.perform(replaceText("password123#$*"));

        ViewInteraction materialButton = onView( withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView( withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));


    }
    /**
     * @Test test the lowercase
     * */
    @Test
    public void testFindMissingLowerCase(){
        ViewInteraction appCompatEditText = onView( withId(R.id.pw));
        appCompatEditText.perform(replaceText("WW123#$*"));

        ViewInteraction materialButton = onView( withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView( withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));


    }
    /**
     * @Test test the number
     * */
    @Test
    public void testFindMissingNumber(){
        ViewInteraction appCompatEditText = onView( withId(R.id.pw));
        appCompatEditText.perform(replaceText("passwordWW#$*"));

        ViewInteraction materialButton = onView( withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView( withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));


    }
    /**
     * @Test test the special chacater
     * */
    @Test
    public void testFindMissSpecialCase(){
        ViewInteraction appCompatEditText = onView( withId(R.id.pw));
        appCompatEditText.perform(replaceText("password123"));

        ViewInteraction materialButton = onView( withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView( withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));


    }

    /**
     * @Test test the lowercase
     * */
    @Test
    public void testFindAll(){
        ViewInteraction appCompatEditText = onView( withId(R.id.pw));
        appCompatEditText.perform(replaceText("passwordWW123#$*"));

        ViewInteraction materialButton = onView( withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView( withId(R.id.textView));
        textView.check(matches(withText("Your password meets the requirements")));


    }
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }


}
