package io.pivotal.labs.delboy.api;

import io.pivotal.labs.delboy.ServiceRepository;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

@Provider
public class ServiceRepositoryProvider implements ContextResolver<ServiceRepository> {

    private ServiceRepository serviceRepository = new ServiceRepository();

    public static ServiceRepository locate(Providers providers) {
        ContextResolver<ServiceRepository> resolver = providers.getContextResolver(ServiceRepository.class, MediaType.WILDCARD_TYPE);
        return resolver.getContext(null);
    }

    @Override
    public ServiceRepository getContext(Class<?> type) {
        return serviceRepository;
    }

}
