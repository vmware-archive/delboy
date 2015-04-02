package io.pivotal.labs.matchers;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static io.pivotal.labs.matchers.MatcherAssertions.*;
import static org.hamcrest.Matchers.contains;

public class JsonArrayMatcherTest {

    @Test
    public void shouldDescribeAnArray() throws Exception {
        assertDescription("an array", JsonMatchers.jsonArray());
    }

    @Test
    public void shouldMatchAnArray() throws Exception {
        assertMatches(JsonMatchers.jsonArray(), CollectionFactory.list());
    }

    @Test
    public void shouldMatchAnArrayWithIrrelevantElements() throws Exception {
        assertMatches(JsonMatchers.jsonArray(), CollectionFactory.list("foo"));
    }

    @Test
    public void shouldNotMatchNull() throws Exception {
        assertDoesNotMatch(JsonMatchers.jsonArray(), null);
        assertMismatchDescription("was null", JsonMatchers.jsonArray(), null);
    }

    @Test
    public void shouldNotMatchANonArray() throws Exception {
        assertDoesNotMatch(JsonMatchers.jsonArray(), "freddy");
        assertDoesNotMatch(JsonMatchers.jsonArray(), 23);
        assertDoesNotMatch(JsonMatchers.jsonArray(), new HashMap<>());
        assertMismatchDescription("was a String", JsonMatchers.jsonArray(), "freddy");
        assertMismatchDescription("was an Integer", JsonMatchers.jsonArray(), 23);
        assertMismatchDescription("was a HashMap", JsonMatchers.jsonArray(), new HashMap<>());
    }

    @Test
    public void shouldDescribeAnArrayUsingAnyOldMatcher() throws Exception {
        assertDescription("an array " + describe(contains("foo")), JsonMatchers.jsonArrayWhich(contains("foo")));
    }

    @Test
    public void shouldMatchAnArrayUsingAnyOldMatcher() throws Exception {
        assertMatches(JsonMatchers.jsonArrayWhich(contains("foo")), CollectionFactory.list("foo"));
    }

    @Test
    public void shouldNotMatchAnArrayUsingJustAnyOldMatcher() throws Exception {
        Matcher<Iterable<? extends String>> matcher = contains("foo");
        List<String> item = CollectionFactory.list("bar");

        assertDoesNotMatch(JsonMatchers.jsonArrayWhich(matcher), item);
        assertMismatchDescription(describeMismatch(matcher, item), JsonMatchers.jsonArrayWhich(matcher), item);
    }

}
