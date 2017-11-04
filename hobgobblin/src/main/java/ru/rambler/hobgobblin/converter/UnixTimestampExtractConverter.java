package ru.rambler.hobgobblin.converter;

import gobblin.configuration.WorkUnitState;
import gobblin.converter.Converter;
import org.apache.commons.lang.NotImplementedException;

public abstract class UnixTimestampExtractConverter extends TimestampExtractConverter {
    public enum FormatType {
        seconds,
        milliseconds,
    }

    protected FormatType formatType;

    @Override
    public String getDefaultTimestampFormat() {
        return "seconds";
    }

    public long castTimestampToMilliseconds(long timestamp) {
        switch (this.formatType) {
            case seconds:
                timestamp = timestamp * 1000L;
                break;
            case milliseconds:
                break;
            default:
                throw new RuntimeException("Invalid format in extractor");
        }
        return timestamp;
    }

    @Override
    public Converter<String, String, byte[], TimestampRecord<String>> init(WorkUnitState workUnit) {
        super.init(workUnit);
        String tsFormat = getTimestampFormat();
        switch (tsFormat) {
            case "unix_seconds":
            case "seconds":
                this.formatType = FormatType.seconds;
                break;
            case "unix_milliseconds":
            case "milliseconds":
                this.formatType = FormatType.milliseconds;
                break;
            default:
                throw new NotImplementedException("Extractor not support type: " + tsFormat);
        }
        return this;
    }
}
