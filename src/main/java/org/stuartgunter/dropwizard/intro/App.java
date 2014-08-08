package org.stuartgunter.dropwizard.intro;

import com.datastax.driver.core.Cluster;
import com.netflix.config.*;
import com.netflix.config.source.ZooKeeperConfigurationSource;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.curator.framework.CuratorFramework;
import org.stuartgunter.dropwizard.intro.cassandra.CassandraConfigurationSource;
import org.stuartgunter.dropwizard.intro.resources.DataResource;

public class App extends Application<AppConfig> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public String getName() {
        return "distributed-config";
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
    }

    @Override
    public void run(final AppConfig config, Environment env) throws Exception {
        switch (config.getConfigType()) {
            case cassandra:
                setupDynamicCassandraConfig(config, env);
                break;
            case zookeeper:
                setupDynamicZooKeeperConfig(config, env);
                break;
        }

        env.jersey().register(new DataResource(new Config()));
    }

    private void setupDynamicCassandraConfig(final AppConfig config, Environment env) throws Exception {
        Cluster cluster = config.getCassandra().build(env);
        PolledConfigurationSource source = new CassandraConfigurationSource(cluster, env.getName());
        AbstractPollingScheduler pollingScheduler = new FixedDelayPollingScheduler(1, 1000, false);

        ConcurrentCompositeConfiguration compositeConfiguration = new ConcurrentCompositeConfiguration();
        compositeConfiguration.addConfiguration(new PropertiesConfiguration("default.properties"));
        compositeConfiguration.addConfigurationAtFront(new DynamicConfiguration(source, pollingScheduler), "cassandra");
        ConfigurationManager.install(compositeConfiguration);
    }

    private void setupDynamicZooKeeperConfig(final AppConfig config, Environment env) {
        final CuratorFramework curator = config.getCurator().build(env);
        env.lifecycle().manage(new Managed() {
            @Override
            public void start() throws Exception {
                final ZooKeeperConfigurationSource zkConfigSource = new ZooKeeperConfigurationSource(curator, config.getZkConfigRoot());
                zkConfigSource.start();

                ConcurrentCompositeConfiguration compositeConfiguration = new ConcurrentCompositeConfiguration();
                compositeConfiguration.addConfiguration(new PropertiesConfiguration("default.properties"));
                compositeConfiguration.addConfigurationAtFront(new DynamicWatchedConfiguration(zkConfigSource), "zookeeper");
                ConfigurationManager.install(compositeConfiguration);
            }

            @Override
            public void stop() throws Exception {
                // do nothing
            }
        });
    }
}
