package io.pivotal.labs.matchers;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static io.pivotal.labs.matchers.CollectionFactory.entry;
import static io.pivotal.labs.matchers.CollectionFactory.map;
import static io.pivotal.labs.matchers.MatcherAssertions.*;
import static org.hamcrest.Matchers.*;

public class JsonObjectMatcherTest {

    @Test
    public void shouldDescribeAnObject() throws Exception {
        assertDescription("an object", JsonMatchers.jsonObject());
    }

    @Test
    public void shouldMatchAnObject() throws Exception {
        assertMatches(JsonMatchers.jsonObject(), map());
    }

    @Test
    public void shouldMatchAnObjectWithIrrelevantProperties() throws Exception {
        assertMatches(JsonMatchers.jsonObject(), map(entry("foo", 1)));
    }

    @Test
    public void shouldNotMatchNull() throws Exception {
        assertDoesNotMatch(JsonMatchers.jsonObject(), null);
        assertMismatchDescription("was null", JsonMatchers.jsonObject(), null);
    }

    @Test
    public void shouldNotMatchANonObject() throws Exception {
        assertDoesNotMatch(JsonMatchers.jsonObject(), "freddy");
        assertDoesNotMatch(JsonMatchers.jsonObject(), 23);
        assertDoesNotMatch(JsonMatchers.jsonObject(), new ArrayList<>());
        assertMismatchDescription("was a String", JsonMatchers.jsonObject(), "freddy");
        assertMismatchDescription("was an Integer", JsonMatchers.jsonObject(), 23);
        assertMismatchDescription("was an ArrayList", JsonMatchers.jsonObject(), new ArrayList<>());
    }

    @Test
    public void shouldDescribeAnObjectWithProperties() throws Exception {
        assertDescription("an object with property foo", JsonMatchers.jsonObjectWith(JsonMatchers.property("foo")));
        assertDescription("an object with properties foo, bar", JsonMatchers.jsonObjectWith(JsonMatchers.property("foo"), JsonMatchers.property("bar")));
    }

    @Test
    public void shouldMatchAnObjectWithProperties() throws Exception {
        assertMatches(JsonMatchers.jsonObjectWith(JsonMatchers.property("foo")), map(entry("foo", "")));
        assertMatches(JsonMatchers.jsonObjectWith(JsonMatchers.property("foo"), JsonMatchers.property("bar")), map(entry("foo", ""), entry("bar", "")));
        assertMatches(JsonMatchers.jsonObjectWith(JsonMatchers.property("bar"), JsonMatchers.property("foo")), map(entry("foo", ""), entry("bar", "")));
    }

    @Test
    public void shouldNotMatchAnObjectWithoutProperties() throws Exception {
        assertDoesNotMatch(JsonMatchers.jsonObjectWith(JsonMatchers.property("foo")), map());
        assertMismatchDescription("property foo was missing", JsonMatchers.jsonObjectWith(JsonMatchers.property("foo")), map());
        assertMismatchDescription("property foo was missing, property bar was missing", JsonMatchers.jsonObjectWith(JsonMatchers.property("foo"), JsonMatchers.property("bar")), map());
    }

    @Test
    public void shouldDescribeAnObjectWithPropertiesWithValues() throws Exception {
        assertDescription("an object with property foo = " + describe(equalTo(1)), JsonMatchers.jsonObjectWith(JsonMatchers.property("foo", equalTo(1))));
        assertDescription("an object with properties foo = " + describe(greaterThan(1)) + ", bar", JsonMatchers.jsonObjectWith(JsonMatchers.property("foo", greaterThan(1)), JsonMatchers.property("bar")));
    }

    @Test
    public void shouldMatchAnObjectWithPropertiesWithValues() throws Exception {
        assertMatches(JsonMatchers.jsonObjectWith(JsonMatchers.property("foo", equalTo(1))), map(entry("foo", 1)));
    }

    @Test
    public void shouldMatchAnObjectWithPropertiesWithValuesAndIrrelevantProperties() throws Exception {
        assertMatches(JsonMatchers.jsonObjectWith(JsonMatchers.property("foo", equalTo(1))), map(entry("foo", 1), entry("bar", 2)));
    }

    @Test
    public void shouldNotMatchAnObjectWithoutPropertiesWithValues() throws Exception {
        assertDoesNotMatch(JsonMatchers.jsonObjectWith(JsonMatchers.property("foo", equalTo(1))), map());
        assertDoesNotMatch(JsonMatchers.jsonObjectWith(JsonMatchers.property("foo", equalTo(1))), map(entry("foo", 2)));
        assertMismatchDescription("property foo " + describeMismatch(equalTo(1), 2), JsonMatchers.jsonObjectWith(JsonMatchers.property("foo", equalTo(1))), map(entry("foo", 2)));
        assertMismatchDescription("property foo was missing, property bar " + describeMismatch(equalTo(2), 1), JsonMatchers.jsonObjectWith(JsonMatchers.property("foo", equalTo(1)), JsonMatchers.property("bar", equalTo(2))), map(entry("bar", 1)));
    }

    @Test
    public void shouldDescribeAnObjectWithOnlyProperties() throws Exception {
        assertDescription("an object with property foo = " + describe(equalTo(1)) + " (only)", JsonMatchers.jsonObjectWithOnly(JsonMatchers.property("foo", equalTo(1))));
    }

    @Test
    public void shouldMatchAnObjectWithOnlyProperties() throws Exception {
        assertMatches(JsonMatchers.jsonObjectWithOnly(JsonMatchers.property("foo", equalTo(1))), map(entry("foo", 1)));
    }

    @Test
    public void shouldNotMatchAnObjectWithNotOnlyProperties() throws Exception {
        assertDoesNotMatch(JsonMatchers.jsonObjectWithOnly(JsonMatchers.property("foo", equalTo(1))), map(entry("foo", 1), entry("bar", 2)));
        assertMismatchDescription("property foo " + describeMismatch(equalTo(1), 2) + ", unexpected property bar was <1>", JsonMatchers.jsonObjectWithOnly(JsonMatchers.property("foo", equalTo(1))), map(entry("foo", 2), entry("bar", 1)));
        assertMismatchDescription("unexpected property bar was <1>", JsonMatchers.jsonObjectWithOnly(), map(entry("bar", 1)));
    }

    @Test
    public void shouldDescribeAnObjectUsingAnyOldMatcher() throws Exception {
        assertDescription("an object " + describe(hasKey("foo")), JsonMatchers.jsonObjectWhich(hasKey("foo")));
    }

    @Test
    public void shouldMatchAnObjectUsingAnyOldMatcher() throws Exception {
        assertMatches(JsonMatchers.jsonObjectWhich(hasKey("foo")), map(entry("foo", 1)));
    }

    @Test
    public void shouldNotMatchAnObjectUsingJustAnyOldMatcher() throws Exception {
        Matcher<Map<? extends String, ?>> matcher = hasKey("bar");
        Map<String, Integer> item = map(entry("foo", 1));

        assertDoesNotMatch(JsonMatchers.jsonObjectWhich(matcher), item);
        assertMismatchDescription(describeMismatch(matcher, item), JsonMatchers.jsonObjectWhich(matcher), item);
    }

}
