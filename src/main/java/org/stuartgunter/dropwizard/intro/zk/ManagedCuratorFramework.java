package org.stuartgunter.dropwizard.intro.zk;

import io.dropwizard.lifecycle.Managed;
import org.apache.curator.framework.CuratorFramework;

class ManagedCuratorFramework implements Managed {

    private final CuratorFramework framework;

    /**
     * Manage the given {@link CuratorFramework} instance.
     *
     * @param framework the Curator instance to manage.
     */
    public ManagedCuratorFramework(final CuratorFramework framework) {
        this.framework = framework;
    }

    @Override
    public void start() throws Exception {
        framework.start();
    }

    @Override
    public void stop() throws Exception {
        framework.close();
    }
}
