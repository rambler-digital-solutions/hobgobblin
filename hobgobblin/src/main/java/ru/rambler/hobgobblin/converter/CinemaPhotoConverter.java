package ru.rambler.hobgobblin.converter;

import gobblin.configuration.WorkUnitState;
import gobblin.converter.Converter;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

public class CinemaPhotoConverter extends ByteArrayConverter {
    private DateTimeFormatter dateTimeParser;
    public static String timeStampFormat = "yyyy-MM-dd HH-mm";

    @Override
    public Converter<String, String, byte[], TimestampRecord<String>> init(WorkUnitState workUnit) {
        super.init(workUnit);
        dateTimeParser = DateTimeFormat.forPattern(timeStampFormat);
        return this;
    }

    @Override
    public TimestampRecord<String> transform(String record) {
        JSONObject jsonObject = new JSONObject(record);
        String location = jsonObject.getString("location");
        String splits[] = location.split("/");
        String day = splits[0];
        String fileNameSplits[] = splits[3].split("_");
        long timestamp = dateTimeParser.parseDateTime(day + " " + fileNameSplits[0]).getMillis();

        return new TimestampRecord<>(record, timestamp);
    }
}
