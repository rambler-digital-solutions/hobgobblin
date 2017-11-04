package ru.rambler.hobgobblin.converter;

import org.testng.annotations.DataProvider;

/**
 * Created by akhlestin on 19.05.17.
 */
public class JsonFormattedTimestampExtractConverterTest extends TimestampExractConverterTest {
    @DataProvider
    @Override
    public Object[][] converterSamples() {
        String sample1 = "{\"ts\": \"2017-05-18 18:08:08\"}";
        String sample2 = "{\"ts\": \"2017-05-18T18:08:08\"}";
        String sample3 = "{\"time_stamp\": \"2017-08-03T10:03:07.11+03:00\"}";
        return new Object[][] {
                {sample1, new Long(1495120088000L), "ts", "yyyy-MM-dd HH:mm:ss"},
                {sample2, new Long(1495120088000L), "ts", "yyyy-MM-dd'T'HH:mm:ss"},
                {sample3, new Long(1501743787110L), "time_stamp", "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZ"},
        };
    }

    @Override
    public TimestampExtractConverter newConverter() {
        return new JsonFormattedTimestampExtractConverter();
    }
}
