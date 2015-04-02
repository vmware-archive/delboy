package io.pivotal.labs.delboy.api;

import io.pivotal.labs.delboy.Catalog;
import io.pivotal.labs.delboy.Plan;
import io.pivotal.labs.delboy.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.UUID;

@Path("/v2/catalog")
public class CatalogResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Catalog getCatalog() {
        Plan plan = new Plan(UUID.randomUUID(), "default", "a plan");
        Catalog catalog = new Catalog(Collections.singletonList(new Service(UUID.randomUUID(), "dynamic-service-broker", "a service", false, Collections.singletonList(plan))));
        return catalog;
    }

}
