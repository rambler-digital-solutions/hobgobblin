package ru.rambler.hobgobblin.converter;

import com.google.gson.Gson;

public class PriceConverter extends ByteArrayConverter {
    private Gson gson = new Gson();

    public class TimestampField {
        Long time;
    }

    @Override
    public TimestampRecord<String> transform(String record) {
        TimestampField tsObj = gson.fromJson(record, TimestampField.class);
        return new TimestampRecord<>(record, tsObj.time*1000L);
    }
}
