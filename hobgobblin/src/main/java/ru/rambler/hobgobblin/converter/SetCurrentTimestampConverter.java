package ru.rambler.hobgobblin.converter;


public class SetCurrentTimestampConverter extends ByteArrayConverter {
    @Override
    public TimestampRecord<String> transform(String record) {
        return new TimestampRecord<>(record, System.currentTimeMillis());
    }
}
