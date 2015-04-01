package io.pivotal.labs.delboy.api;

import io.pivotal.labs.delboy.test.JerseyTestRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static io.pivotal.labs.delboy.test.MapMatchers.entries;
import static io.pivotal.labs.delboy.test.MapMatchers.entry;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class CatalogResourceTests {

    @Rule
    public final JerseyTestRule jersey = new JerseyTestRule(CatalogResource.class);

    @Test
    public void shouldReturnACatalogRootedInAListOfServices() {
        Map<String, Object> catalog = jersey.target("/v2/catalog").request().get(Map.class);
        assertThat(catalog, entries(contains(entry("services", instanceOf(Set.class)))));
    }

}
