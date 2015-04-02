package io.pivotal.labs.delboy.api;

import io.pivotal.labs.delboy.test.JerseyTestRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Map;

import static io.pivotal.labs.matchers.JsonMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class CatalogResourceTests {

    @Rule
    public final JerseyTestRule jersey = new JerseyTestRule(CatalogResource.class);

    @Test
    public void shouldReturnACatalogRootedInAListOfServicesContainingOnlyItself() {
        Object catalog = jersey.target("/v2/catalog").request().get(Map.class);

        assertThat(catalog, jsonObjectWith(
                property("services", jsonArrayWhich(contains(
                        jsonObjectWith(
                                property("id", not(isEmptyOrNullString())),
                                property("name", equalTo("dynamic-service-broker")),
                                property("description", not(isEmptyOrNullString())),
                                property("bindable", equalTo(false)),
                                property("plans", jsonArrayWhich(contains(
                                        jsonObjectWith(
                                                property("id", not(isEmptyOrNullString())),
                                                property("name", equalTo("default")),
                                                property("description", not(isEmptyOrNullString()))))))))))));
    }

}
