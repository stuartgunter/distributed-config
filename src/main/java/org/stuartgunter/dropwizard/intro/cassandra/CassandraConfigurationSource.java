package org.stuartgunter.dropwizard.intro.cassandra;

import com.datastax.driver.core.*;
import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CassandraConfigurationSource implements PolledConfigurationSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraConfigurationSource.class);

    private final PreparedStatement statement;
    private final Session configKeyspace;

    public CassandraConfigurationSource(Cluster cluster, String appName) {
        configKeyspace = cluster.connect("config");
        statement = configKeyspace.prepare("select * from config where app_name = '" + appName + "'");
    }

    @Override
    public PollResult poll(boolean initial, Object checkPoint) throws Exception {
        ResultSet config = configKeyspace.execute(statement.bind());
        Map<String, Object> configMap = new HashMap<>();
        for (Row row : config) {
            configMap.put(row.getString("property_name"), row.getString("property_value"));
        }
        LOGGER.debug("Updated config from cassandra {}", configMap);
        return PollResult.createFull(configMap);
    }
}
