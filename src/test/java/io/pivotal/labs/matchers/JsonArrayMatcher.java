package io.pivotal.labs.matchers;

import org.hamcrest.Matcher;

public class JsonArrayMatcher extends CastingMatcher<Iterable> {

    public static Matcher<Object> jsonArray() {
        return new JsonArrayMatcher(null);
    }

    public static Matcher<Object> jsonArrayWhich(Matcher<? extends Iterable> elementsMatcher) {
        return new JsonArrayMatcher(elementsMatcher);
    }

    private JsonArrayMatcher(Matcher<? extends Iterable> elementsMatcher) {
        super(Iterable.class, "an array", elementsMatcher);
    }

}
