package ru.rambler.hobgobblin.converter;

public class Top100SDKConverter extends ByteArrayConverter {
    @Override
    public TimestampRecord<String> transform(String record) {
        String[] splittedString = record.split("\t");
        double timestamp_double = Double.parseDouble(splittedString[2]);
        return new TimestampRecord<>(record, (long)(timestamp_double*1000));
    }
}
