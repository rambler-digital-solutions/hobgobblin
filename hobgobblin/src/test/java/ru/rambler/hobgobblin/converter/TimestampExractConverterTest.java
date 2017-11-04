package ru.rambler.hobgobblin.converter;


import gobblin.configuration.WorkUnitState;
import org.testng.annotations.DataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Properties;

public abstract class TimestampExractConverterTest extends Assert {
    @DataProvider
    public abstract Object[][] converterSamples();

    public abstract TimestampExtractConverter newConverter();

    public TimestampExtractConverter makeConverter(String field, String format){
        Properties props = new Properties();
        props.setProperty(TimestampExtractConverter.TIMESTAMP_FIELD, field);
        props.setProperty(TimestampExtractConverter.TIMESTAMP_FORMAT, format);
        WorkUnitState workUnitState = new WorkUnitState();
        workUnitState.addAll(props);
        TimestampExtractConverter converter = newConverter();
        converter.init(workUnitState);
        return converter;
    };

    @Test(dataProvider = "converterSamples")
    public void testCheckConvert(String input, Long expect, String field, String format ) {
        assertTrue(makeConverter(field, format).transform(input).getTimestamp() == expect.longValue());
    }
}
