package ru.rambler.hobgobblin.converter;

import org.json.JSONObject;

public class JsonUnixTimestampExtractConverter extends UnixTimestampExtractConverter {
    @Override
    public TimestampRecord<String> transform(String record) {
        JSONObject jsonObject = new JSONObject(record);
        long timestamp = jsonObject.getLong(getTimestampField());
        timestamp = castTimestampToMilliseconds(timestamp);
        return new TimestampRecord<>(record, timestamp);
    }
}
