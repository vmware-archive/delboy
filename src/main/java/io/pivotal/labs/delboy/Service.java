package io.pivotal.labs.delboy;

import java.util.List;
import java.util.UUID;

public class Service {

    private UUID id;
    private String name;
    private String description;
    private boolean bindable;
    private List<Plan> plans;

    public Service(UUID id, String name, String description, boolean bindable, List<Plan> plans) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.bindable = bindable;
        this.plans = plans;
    }

    public String getDescription() {
        return description;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isBindable() {
        return bindable;
    }

    public List<Plan> getPlans() {
        return plans;
    }

}
