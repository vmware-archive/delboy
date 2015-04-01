package io.pivotal.labs.delboy.test;

import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MapMatchers {

    public static <K, V> Matcher<Map<K, V>> entries(Matcher<Iterable<? extends Map.Entry<K, V>>> matcher) {
        return new FeatureMatcher<Map<K, V>, Set<Map.Entry<K, V>>>(matcher, "entries", "entries") {
            @Override
            protected Set<Map.Entry<K, V>> featureValueOf(Map<K, V> actual) {
                return actual.entrySet();
            }
        };
    }

    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<K, V>(key, value);
    }

    public static <K, V> Matcher<Map.Entry<K, V>> entry(K key, Matcher<V> valueMatcher) {
        return new TypeSafeDiagnosingMatcher<Map.Entry<K, V>>() {
            @Override
            protected boolean matchesSafely(Map.Entry<K, V> item, Description mismatchDescription) {
                boolean keyMatches = Objects.equals(item.getKey(), key);
                boolean valueMatches = valueMatcher.matches(item.getValue());

                if (keyMatches && valueMatches) {
                    return true;
                } else {
                    mismatchDescription.appendValue(item.getKey());
                    mismatchDescription.appendText("->");
                    mismatchDescription.appendValue(item.getValue());
                    if (!valueMatches) {
                        mismatchDescription.appendText(" (");
                        valueMatcher.describeMismatch(item.getValue(), mismatchDescription);
                        mismatchDescription.appendText(")");
                    }
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendValue(key).appendText("->").appendDescriptionOf(valueMatcher);
            }
        };
    }

}
