package ru.rambler.hobgobblin.converter;

import org.testng.annotations.DataProvider;

public class JsonUnixTimestampExtractConverterTest extends TimestampExractConverterTest {
    @DataProvider
    @Override
    public Object[][] converterSamples() {
        String sample1 = "{\"time\": 8888}";
        String sample2 = "{\"ts\": \"8888\"}";
        return new Object[][] {
                {sample1, new Long(8888000L), "time", "unix_seconds"},
                {sample1, new Long(8888L), "time", "milliseconds"},
                {sample2, new Long(8888L), "ts", "milliseconds"},
        };
    }

    @Override
    public TimestampExtractConverter newConverter() {
        return new JsonUnixTimestampExtractConverter();
    }
}
