package io.pivotal.labs.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Arrays;
import java.util.Map;

public class PropertiesMatcher extends TypeSafeDiagnosingMatcher<Map<? extends String, ?>> {

    public static PropertiesMatcher hasProperties(Property... properties) {
        return new PropertiesMatcher(properties);
    }

    private final Property[] properties;

    private PropertiesMatcher(Property[] properties) {
        this.properties = properties;
    }

    @Override
    protected boolean matchesSafely(Map<? extends String, ?> item, Description mismatchDescription) {
        boolean propertiesMatch = true;
        for (Property property : properties) {
            String propertyName = property.getKey();
            Matcher<?> propertyMatcher = property.getValue();
            if (!item.keySet().contains(propertyName)) {
                describePropertyMismatch(propertiesMatch, propertyName, mismatchDescription).appendText(" was missing");
                propertiesMatch = false;
            } else if (propertyMatcher != null && !propertyMatcher.matches(item.get(propertyName))) {
                describePropertyMismatch(propertiesMatch, propertyName, mismatchDescription).appendText(" ").appendDescriptionOf(mismatch(propertyMatcher, item.get(propertyName)));
                propertiesMatch = false;
            }
        }
        return propertiesMatch;
    }

    private Description describePropertyMismatch(boolean hithertoMatches, String propertyName, Description mismatchDescription) {
        if (!hithertoMatches) mismatchDescription.appendText(", ");
        return mismatchDescription.appendText("property ").appendText(propertyName);
    }

    private SelfDescribing mismatch(Matcher<?> matcher, Object item) {
        return d -> matcher.describeMismatch(item, d);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(properties.length > 1 ? "with properties " : "with property ");
        description.appendList("", ", ", "", Arrays.asList(properties));
    }

}
