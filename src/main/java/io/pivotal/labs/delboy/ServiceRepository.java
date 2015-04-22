package io.pivotal.labs.delboy;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ServiceRepository {

    private Set<Service> services = new HashSet<>();

    public void persist(Service service) {
        services.add(service);
    }

    public Collection<? extends Service> findAll() {
        return Collections.unmodifiableSet(services);
    }

}
