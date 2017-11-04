package ru.rambler.hobgobblin.metrics.graphite;

import com.google.common.base.Preconditions;
import gobblin.configuration.ConfigurationKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class GraphiteSettings {
    private static final Logger LOGGER = LoggerFactory.getLogger(GraphiteSettings.class);
    public static final String METRICS_REPORTING_PREFIX = ConfigurationKeys.METRICS_CONFIGURATIONS_PREFIX +
            "reporting.graphite.prefix";

    private String prefix;
    private String hostname;
    private int port;
    private GraphiteConnectionType connectionType;

    public GraphiteSettings(Properties properties) {
        try {
            Preconditions.checkArgument(properties.containsKey(ConfigurationKeys.METRICS_REPORTING_GRAPHITE_HOSTNAME),
                    "Graphite hostname is missing.");
        } catch (IllegalArgumentException exception) {
            LOGGER.error("Not reporting to Graphite due to missing Graphite configuration(s).", exception);
        }

        hostname = properties.getProperty(ConfigurationKeys.METRICS_REPORTING_GRAPHITE_HOSTNAME);
        port = Integer.parseInt(properties.getProperty(ConfigurationKeys.METRICS_REPORTING_GRAPHITE_PORT,
                ConfigurationKeys.DEFAULT_METRICS_REPORTING_GRAPHITE_PORT));

        String type = properties.getProperty(ConfigurationKeys.METRICS_REPORTING_GRAPHITE_SENDING_TYPE,
                ConfigurationKeys.DEFAULT_METRICS_REPORTING_GRAPHITE_SENDING_TYPE).toUpperCase();
        try {
            connectionType = GraphiteConnectionType.valueOf(type);
        } catch (IllegalArgumentException exception) {
            LOGGER.warn("Graphite Reporter connection type " + type + " not recognized. Will use TCP for sending.", exception);
            connectionType = GraphiteConnectionType.TCP;
        }

        try {
            Preconditions.checkArgument(properties.containsKey(METRICS_REPORTING_PREFIX),
                    "Graphite prefix is missing.");
        } catch (IllegalArgumentException exception) {
            LOGGER.error("Not reporting to Graphite due to missing Graphite configuration(s).", exception);
        }
        prefix = properties.getProperty(METRICS_REPORTING_PREFIX);
    }

    public String getPrefix() { return prefix; }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public GraphiteConnectionType getConnectionType() {
        return connectionType;
    }
}
