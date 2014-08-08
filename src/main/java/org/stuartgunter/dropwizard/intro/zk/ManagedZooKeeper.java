package org.stuartgunter.dropwizard.intro.zk;

import io.dropwizard.lifecycle.Managed;
import org.apache.zookeeper.ZooKeeper;

public class ManagedZooKeeper implements Managed {

    private final ZooKeeper client;

    /**
     * Manage the given {@link ZooKeeper} client instance.
     *
     * @param client the client to manage.
     */
    public ManagedZooKeeper(final ZooKeeper client) {
        this.client = client;
    }

    @Override
    public void start() throws Exception {
        // already started, nothing to do
    }

    @Override
    public void stop() throws Exception {
        client.close();
    }
}