package ru.rambler.hobgobblin.converter;


import com.typesafe.config.Config;
import gobblin.configuration.WorkUnitState;
import gobblin.converter.Converter;
import gobblin.util.ConfigUtils;

abstract public class TimestampExtractConverter extends ByteArrayConverter {
    public static final String TIMESTAMP_FIELD = "converter.timestamp.field";
    public static final String TIMESTAMP_FORMAT = "converter.timestamp.format";
    public static final String DEFAULT_TIMESTAMP_FIELD = "timestamp";

    private String timestampField;
    private String timestampFormat;

    public String getTimestampField() {
        return timestampField;
    }

    public String getTimestampFormat() {
        return timestampFormat;
    }

    public abstract String getDefaultTimestampFormat();

    @Override
    public Converter<String, String, byte[], TimestampRecord<String>> init(WorkUnitState workUnit) {
        super.init(workUnit);
        Config config = ConfigUtils.propertiesToConfig(workUnit.getProperties());
        this.timestampField = ConfigUtils.getString(config, TIMESTAMP_FIELD, DEFAULT_TIMESTAMP_FIELD);
        this.timestampFormat = ConfigUtils.getString(config, TIMESTAMP_FORMAT, getDefaultTimestampFormat());
        return this;
    }
}
