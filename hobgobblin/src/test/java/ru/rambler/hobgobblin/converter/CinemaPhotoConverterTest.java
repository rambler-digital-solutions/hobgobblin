package ru.rambler.hobgobblin.converter;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

public class CinemaPhotoConverterTest extends ConverterAssert {
    @DataProvider
    @Override
    public Object[][] converterSamples() {
        String sample = "{\"event_ts\": 1508798296, \"created_ts\": 1508798190, " +
                "\"location\": \"2017-05-18/%D0%9B%D1%83%D0%B1%D1%8F%D0%BD%D0%BA%D0%B0/%D0%97%D0%B0%D0%BB_2/18-08_img_5.jpg\"}";
        return new Object[][] {
                {sample, new TimestampRecord<String>(sample, 1495120080000L) },
        };
    }

    @BeforeMethod
    public void setUp() throws Exception {
        this.converter = new CinemaPhotoConverter();
        initConverter();
    }
}
