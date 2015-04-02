package io.pivotal.labs.matchers;

import org.hamcrest.Matcher;

import java.util.Map;

public class JsonMatchers {

    public static Matcher<Object> jsonObject() {
        return JsonObjectMatcher.jsonObject();
    }

    public static Matcher<Object> jsonObjectWith(Property... properties) {
        return JsonObjectMatcher.jsonObjectWith(properties);
    }

    public static Matcher<Object> jsonObjectWithOnly(Property... properties) {
        return JsonObjectMatcher.jsonObjectWithOnly(properties);
    }

    public static Matcher<Object> jsonObjectWhich(Matcher<Map<? extends String, ?>> propertiesMatcher) {
        return JsonObjectMatcher.jsonObjectWhich(propertiesMatcher);
    }

    public static Matcher<Map<? extends String, ?>> hasProperties(Property... properties) {
        return PropertiesMatcher.hasProperties(properties);
    }

    public static Matcher<Map<? extends String, ?>> hasOnlyProperties(Property... properties) {
        return OnlyPropertiesMatcher.hasOnlyProperties(properties);
    }

    public static Property property(String name) {
        return Property.property(name);
    }

    public static Property property(String name, Matcher<?> valueMatcher) {
        return Property.property(name, valueMatcher);
    }

    public static Matcher<Object> jsonArray() {
        return JsonArrayMatcher.jsonArray();
    }

    public static Matcher<Object> jsonArrayWhich(Matcher<? extends Iterable> elementsMatcher) {
        return JsonArrayMatcher.jsonArrayWhich(elementsMatcher);
    }

}
