package io.pivotal.labs.delboy.api;

import io.pivotal.labs.delboy.Plan;
import io.pivotal.labs.delboy.Service;
import io.pivotal.labs.delboy.ServiceRepository;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import java.util.Collections;
import java.util.UUID;

@Path("/services")
public class ServicesResource {

    public static class CreateServiceRequest {
        public String name;
        public String description;
    }

    @Context
    private Providers providers;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Service createService(CreateServiceRequest request) {
        ServiceRepository serviceRepository = ServiceRepositoryProvider.locate(providers);
        Service service = new Service(UUID.randomUUID(), request.name, request.description, true, Collections.singletonList(new Plan(UUID.randomUUID(), "default", "")));
        serviceRepository.persist(service);
        return service;
    }

}
