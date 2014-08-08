package org.stuartgunter.dropwizard.intro.resources;

import org.stuartgunter.dropwizard.intro.Config;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class DataResource {

    private final Config config;

    public DataResource(Config config) {
        this.config = config;
    }

    @GET
    @Path("kill-switch")
    public String getMode() {
        return config.getKillSwitch() ? "kill mode\n" : "normal operation\n";
    }

    @GET
    @Path("message")
    public String getMessage() {
        return config.getMessage() + "\n";
    }
}
