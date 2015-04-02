package io.pivotal.labs.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;

import java.util.AbstractMap;

public class Property extends AbstractMap.SimpleImmutableEntry<String, Matcher<?>> implements SelfDescribing {

    public static Property property(String name) {
        return new Property(name, null);
    }

    public static Property property(String name, Matcher<?> valueMatcher) {
        return new Property(name, valueMatcher);
    }

    private Property(String name, Matcher<?> valueMatcher) {
        super(name, valueMatcher);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(getKey());
        if (getValue() != null) description.appendText(" = ").appendDescriptionOf(getValue());
    }

}
