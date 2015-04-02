package io.pivotal.labs.delboy;

import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private List<Service> services = new ArrayList<>();

    public Catalog(List<Service> services) {
        this.services = services;
    }

    public List<Service> getServices() {
        return services;
    }

}
