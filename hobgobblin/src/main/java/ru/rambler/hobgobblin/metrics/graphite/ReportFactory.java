package ru.rambler.hobgobblin.metrics.graphite;

import gobblin.metrics.CustomReporterFactory;
import gobblin.metrics.reporter.ScheduledReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class ReportFactory implements CustomReporterFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportFactory.class);

    @Override
    public ScheduledReporter newScheduledReporter(Properties properties) throws IOException {
        LOGGER.info("Reporting metrics to Graphite via customized metrics reporter");
        GraphiteSettings settings = new GraphiteSettings(properties);

        return GraphiteReporter.Factory.newBuilder().withConnectionType(settings.getConnectionType())
                .withConnection(settings.getHostname(), settings.getPort()).withMetricContextName(
                settings.getPrefix()).build(properties);
    }
}
