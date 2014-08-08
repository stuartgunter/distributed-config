package org.stuartgunter.dropwizard.intro.zk;

import io.dropwizard.lifecycle.Managed;
import org.apache.curator.framework.CuratorFramework;

/**
 * This class was copied from https://github.com/datasift/dropwizard-extra. No copyright notice was on the
 * source, so I hope this is sufficient acknowledgement.
 *
 * Source was copied rather than depending via Maven because the latest release only supports Dropwizard 0.6.2
 */
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
