package ru.rambler.hobgobblin.converter;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class PriceConverterTest extends ConverterAssert {
    @DataProvider
    public Object[][] converterSamples() {
        String sample1 = "{\"city_id\":1,\"link_timestamp\":\"2017-03-31 10:33:39.772895\",\"block\":0,\"time\":1490945974}";
        return new Object[][]{
            {sample1, new TimestampRecord<String>(sample1, 1490945974000L)},
        };
    }

    @DataProvider
    public Object[][] npeSamples() {
        return new Object[][]{
                {"",},
                {null,},
        };
    }

    @DataProvider
    public Object[][] invalidJsonSamples() {
        return new Object[][]{
                {"{wrong: }"},
                {"[\"one\", 1]"},
        };
    }

    @BeforeMethod
    public void setUp() throws Exception {
        this.converter = new PriceConverter();
        initConverter();
    }

    @Test(dataProvider = "npeSamples", expectedExceptions = NullPointerException.class)
    public void testNpeCrash(String input) {
        converter.transform(input);
    }

    @Test(dataProvider = "invalidJsonSamples", expectedExceptions = com.google.gson.JsonSyntaxException.class)
    public void testInvalidJson(String input) {
        converter.transform(input);
    }
}
