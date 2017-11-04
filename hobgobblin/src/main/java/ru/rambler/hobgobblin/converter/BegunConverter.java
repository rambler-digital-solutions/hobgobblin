package ru.rambler.hobgobblin.converter;

public class BegunConverter extends ByteArrayConverter {
    @Override
    public TimestampRecord<String> transform(String record) {
        String begunTimestamp = record.split("\\s+", 3)[0]; // "@unix_timestamp"
        long timestamp = Long.parseLong(begunTimestamp.substring(begunTimestamp.indexOf('@') + 1)) * 1000;
        return new TimestampRecord<>(record, timestamp);
    }
}
