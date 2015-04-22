package io.pivotal.labs.delboy.api;

import io.pivotal.labs.delboy.Catalog;
import io.pivotal.labs.delboy.Plan;
import io.pivotal.labs.delboy.Service;
import io.pivotal.labs.delboy.ServiceRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Path("/v2/catalog")
public class CatalogResource {

    @Context
    private Providers providers;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Catalog getCatalog() {
        ServiceRepository serviceRepository = ServiceRepositoryProvider.locate(providers);
        Plan plan = new Plan(UUID.randomUUID(), "default", "a plan");
        Service self = new Service(UUID.randomUUID(), "dynamic-service-broker", "a service", true, Collections.singletonList(plan));

        List<Service> services = new ArrayList<>();
        services.add(self);
        services.addAll(serviceRepository.findAll());

        Catalog catalog = new Catalog(services);
        return catalog;
    }

}
