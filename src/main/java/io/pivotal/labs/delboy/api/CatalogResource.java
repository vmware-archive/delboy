package io.pivotal.labs.delboy.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Map;

@Path("/v2/catalog")
public class CatalogResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getCatalog() {
        return Collections.singletonMap("services", Collections.emptyList());
    }

}
