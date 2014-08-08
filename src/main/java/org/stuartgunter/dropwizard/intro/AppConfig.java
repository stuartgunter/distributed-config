package org.stuartgunter.dropwizard.intro;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;
import org.hibernate.validator.constraints.NotEmpty;
import org.stuartgunter.dropwizard.cassandra.CassandraFactory;
import org.stuartgunter.dropwizard.intro.zk.CuratorFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AppConfig extends Configuration {

    enum ConfigType {
        zookeeper,
        cassandra
    }

    @NotNull
    private ConfigType configType;

    @NotEmpty
    private String zkConfigRoot;

    @Valid
    @NotNull
    private CuratorFactory curator = new CuratorFactory();

    @Valid
    @NotNull
    private HttpClientConfiguration httpClient = new HttpClientConfiguration();

    @Valid
    @NotNull
    private CassandraFactory cassandra = new CassandraFactory();

    public ConfigType getConfigType() {
        return configType;
    }

    public String getZkConfigRoot() {
        return zkConfigRoot;
    }

    public CuratorFactory getCurator() {
        return curator;
    }

    public HttpClientConfiguration getHttpClient() {
        return httpClient;
    }

    public CassandraFactory getCassandra() {
        return cassandra;
    }
}
