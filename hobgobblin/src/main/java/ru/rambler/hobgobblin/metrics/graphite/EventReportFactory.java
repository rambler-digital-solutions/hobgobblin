package ru.rambler.hobgobblin.metrics.graphite;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import gobblin.metrics.RootMetricContext;
import gobblin.metrics.CustomCodahaleReporterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;


public class EventReportFactory implements CustomCodahaleReporterFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventReportFactory.class);


    @Override
    public ScheduledReporter newScheduledReporter(MetricRegistry registry, Properties properties) throws IOException {
        LOGGER.info("Reporting metrics to Graphite via customized event reporter");
        GraphiteSettings settings = new GraphiteSettings(properties);

        return GraphiteEventReporter.Factory.forContext(RootMetricContext.get())
                .withConnectionType(settings.getConnectionType())
                .withConnection(settings.getHostname(), settings.getPort())
                .withPrefix(settings.getPrefix())
                .build();
    }
}
