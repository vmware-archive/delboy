package io.pivotal.labs.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OnlyPropertiesMatcher extends TypeSafeDiagnosingMatcher<Map<? extends String, ?>> {

    public static OnlyPropertiesMatcher hasOnlyProperties(Property... properties) {
        return new OnlyPropertiesMatcher(properties);
    }

    private final Set<String> propertyNames;
    private final Matcher<Map<? extends String, ?>> positive;

    private OnlyPropertiesMatcher(Property[] properties) {
        propertyNames = Stream.of(properties).map(Map.Entry::getKey).collect(Collectors.toSet());
        positive = PropertiesMatcher.hasProperties(properties);
    }

    @Override
    protected boolean matchesSafely(Map<? extends String, ?> item, Description mismatchDescription) {
        boolean matches = positive.matches(item);
        if (!matches) positive.describeMismatch(item, mismatchDescription);
        for (String propertyName : item.keySet()) {
            if (!propertyNames.contains(propertyName)) {
                if (!matches) mismatchDescription.appendText(", ");
                mismatchDescription.appendText("unexpected property ").appendText(propertyName).appendText(" was ").appendValue(item.get(propertyName));
                matches = false;
            }
        }
        return matches;
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(positive).appendText(" (only)");
    }

}
