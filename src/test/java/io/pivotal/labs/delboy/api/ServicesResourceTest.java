package io.pivotal.labs.delboy.api;

import io.pivotal.labs.delboy.test.JerseyTestRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import java.util.List;
import java.util.Map;

import static io.pivotal.labs.matchers.CollectionFactory.entry;
import static io.pivotal.labs.matchers.CollectionFactory.map;
import static io.pivotal.labs.matchers.JsonMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ServicesResourceTest {

    @Rule
    public final JerseyTestRule jersey = new JerseyTestRule(ServicesResource.class, CatalogResource.class);

    @Test
    public void postCreatesANewService() throws Exception {
        Map<String, Object> request = map(entry("name", "testservice"), entry("description", "A test service"));

        Object service = jersey.target("/services").request().post(Entity.json(request), Map.class);

        assertThat(service, jsonObjectWith(
                property("id", not(isEmptyOrNullString())),
                property("name", equalTo("testservice")),
                property("description", equalTo("A test service")),
                property("bindable", equalTo(true)),
                property("plans", jsonArrayWhich(contains(
                        jsonObjectWith(
                                property("id", not(isEmptyOrNullString())),
                                property("name", equalTo("default"))))))));
    }

    @Ignore("work in progress")
    @Test
    public void postCreatesANewServiceWhichIsListedInTheCatalog() throws Exception {
        Map<String, Object> request = map(entry("name", "testservice"), entry("description", "A test service"));

        Object service = jersey.target("/services").request().post(Entity.json(request), Map.class);

        // we should find a better way to do this
        String serviceId = (String) ((Map<?, ?>) service).get("id");
        String planId = (String) ((Map<?, ?>) ((List<?>) ((Map<?, ?>) service).get("plans")).get(0)).get("id");

        Object catalog = jersey.target("/v2/catalog").request().get(Map.class);

        assertThat(catalog, jsonObjectWith(
                property("services", jsonArrayWhich(hasItem(
                        jsonObjectWith(
                                property("id", equalTo(serviceId)),
                                property("name", equalTo("testservice")),
                                property("description", equalTo("A test service")),
                                property("bindable", equalTo(true)),
                                property("plans", jsonArrayWhich(contains(
                                        jsonObjectWith(
                                                property("id", equalTo(planId)),
                                                property("name", equalTo("default"))))))))))));
    }

}
