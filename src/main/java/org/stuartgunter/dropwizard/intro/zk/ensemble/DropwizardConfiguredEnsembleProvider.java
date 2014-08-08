package org.stuartgunter.dropwizard.intro.zk.ensemble;

import org.apache.curator.ensemble.EnsembleProvider;
import org.stuartgunter.dropwizard.intro.zk.ZooKeeperFactory;

import java.io.IOException;

/**
 * This class was copied from https://github.com/datasift/dropwizard-extra. No copyright notice was on the
 * source, so I hope this is sufficient acknowledgement.
 *
 * Source was copied rather than depending via Maven because the latest release only supports Dropwizard 0.6.2
 */
public class DropwizardConfiguredEnsembleProvider implements EnsembleProvider {

    private final ZooKeeperFactory factory;

    /**
     * Initializes this provider with the given {@code configuration}.
     *
     * @param factory a factory for ZooKeeper client instances.
     */
    public DropwizardConfiguredEnsembleProvider(final ZooKeeperFactory factory) {
        this.factory = factory;
    }

    @Override
    public void start() throws Exception {
        // nothing to do
    }

    @Override
    public String getConnectionString() {
        return factory.getQuorumSpec();
    }

    @Override
    public void close() throws IOException {
        // nothing to do
    }
}