package io.pivotal.labs.delboy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class Service {

    @Id
    private String id;
    private String name;
    private String description;
    private boolean bindable;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Plan> plans;

    public Service(UUID id, String name, String description, boolean bindable, List<Plan> plans) {
        this.id = id.toString();
        this.name = name;
        this.description = description;
        this.bindable = bindable;
        this.plans = plans;
    }

    protected Service() {
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
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
