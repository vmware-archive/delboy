package io.pivotal.labs.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.regex.Pattern;

public abstract class CastingMatcher<T> extends BaseMatcher<Object> {

    private static final Pattern WORD_STARTING_WITH_A_VOWEL = Pattern.compile("^([aeiou]|ho)", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    private final Class<T> type;
    private final String typeDescription;
    private final Matcher<? extends T> matcher;

    protected CastingMatcher(Class<T> type, String typeDescription, Matcher<? extends T> matcher) {
        this.type = type;
        this.typeDescription = typeDescription;
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Object item) {
        return critique(item, new Description.NullDescription());
    }

    @Override
    public void describeMismatch(Object item, Description mismatchDescription) {
        critique(item, mismatchDescription);
    }

    private boolean critique(Object item, Description mismatchDescription) {
        if (item == null) {
            describeNullMismatch(mismatchDescription);
            return false;
        } else if (!(type.isInstance(item))) {
            describeTypeMismatch(item, mismatchDescription);
            return false;
        } else if (matcher != null && !matcher.matches(item)) {
            matcher.describeMismatch(item, mismatchDescription);
            return false;
        } else {
            return true;
        }
    }

    private void describeNullMismatch(Description mismatchDescription) {
        mismatchDescription.appendText("was null");
    }

    private void describeTypeMismatch(Object item, Description mismatchDescription) {
        String typeName = item.getClass().getSimpleName();
        mismatchDescription.appendText("was ").appendText(indefiniteArticleFor(typeName)).appendText(" ").appendText(typeName);
    }

    private static String indefiniteArticleFor(String noun) {
        return WORD_STARTING_WITH_A_VOWEL.matcher(noun).find() ? "an" : "a";
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(typeDescription);
        if (matcher != null) {
            description.appendText(" ").appendDescriptionOf(matcher);
        }
    }

}
