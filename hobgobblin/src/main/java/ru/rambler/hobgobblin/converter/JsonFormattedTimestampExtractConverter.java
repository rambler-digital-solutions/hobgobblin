package ru.rambler.hobgobblin.converter;


import gobblin.configuration.WorkUnitState;
import gobblin.converter.Converter;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

public class JsonFormattedTimestampExtractConverter extends TimestampExtractConverter {
    private DateTimeFormatter dateTimeParser;

    @Override
    public String getDefaultTimestampFormat() {
        return "yyyy-MM-dd'T'HH:mm:ss";
    }

    @Override
    public Converter<String, String, byte[], TimestampRecord<String>> init(WorkUnitState workUnit) {
        super.init(workUnit);
        dateTimeParser = DateTimeFormat.forPattern(this.getTimestampFormat());
        return this;
    }

    @Override
    public TimestampRecord<String> transform(String record) {
        JSONObject jsonObject = new JSONObject(record);
        long timestamp = dateTimeParser.parseDateTime(jsonObject.getString(getTimestampField())).getMillis();
        return new TimestampRecord<>(record, timestamp);
    }
}
