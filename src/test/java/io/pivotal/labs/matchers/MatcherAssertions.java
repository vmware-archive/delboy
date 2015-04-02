package io.pivotal.labs.matchers;

import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

import static org.junit.Assert.*;

public class MatcherAssertions {

    public static <T> void assertDescription(String expectedDescription, Matcher<T> matcher) {
        assertEquals(expectedDescription, describe(matcher));
    }

    public static String describe(SelfDescribing selfDescribing) {
        StringDescription description = new StringDescription();
        selfDescribing.describeTo(description);
        return description.toString();
    }

    public static <T> void assertMatches(Matcher<T> matcher, T item) {
        assertTrue(message(matcher, item, "did not match"), matcher.matches(item));
    }

    public static <T> void assertDoesNotMatch(Matcher<T> matcher, T item) {
        assertFalse(message(matcher, item, "matched"), matcher.matches(item));
    }

    private static <T> String message(Matcher<T> matcher, T item, String condition) {
        StringDescription description = new StringDescription();
        description.appendText("matcher <").appendDescriptionOf(matcher).appendText("> ").appendText(condition).appendText(" item ").appendValue(item);
        return description.toString();
    }

    public static <T> void assertMismatchDescription(String expectedDescription, Matcher<T> matcher, T item) {
        assertEquals(expectedDescription, describeMismatch(matcher, item));
    }

    public static <T> String describeMismatch(Matcher<T> matcher, T item) {
        StringDescription mismatchDescription = new StringDescription();
        matcher.describeMismatch(item, mismatchDescription);
        return mismatchDescription.toString();
    }

}
