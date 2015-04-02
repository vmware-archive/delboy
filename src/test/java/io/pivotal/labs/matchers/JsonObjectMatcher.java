package io.pivotal.labs.matchers;

import org.hamcrest.Matcher;

import java.util.Map;

public class JsonObjectMatcher extends CastingMatcher<Map> {

    public static Matcher<Object> jsonObject() {
        return new JsonObjectMatcher(null);
    }

    public static Matcher<Object> jsonObjectWith(Property... properties) {
        return new JsonObjectMatcher(PropertiesMatcher.hasProperties(properties));
    }

    public static Matcher<Object> jsonObjectWithOnly(Property... properties) {
        return new JsonObjectMatcher(OnlyPropertiesMatcher.hasOnlyProperties(properties));
    }

    public static Matcher<Object> jsonObjectWhich(Matcher<Map<? extends String, ?>> propertiesMatcher) {
        return new JsonObjectMatcher(propertiesMatcher);
    }

    private JsonObjectMatcher(Matcher<Map<? extends String, ?>> propertiesMatcher) {
        super(Map.class, "an object", propertiesMatcher);
    }

}
