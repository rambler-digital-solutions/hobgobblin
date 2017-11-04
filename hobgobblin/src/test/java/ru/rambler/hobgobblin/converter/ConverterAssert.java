package ru.rambler.hobgobblin.converter;

import gobblin.configuration.WorkUnitState;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Properties;


public abstract class ConverterAssert extends Assert{
    @DataProvider
    public abstract Object[][] converterSamples();
    ByteArrayConverter converter;

    public void initConverter(){
        Properties props = new Properties();
        WorkUnitState workUnitState = new WorkUnitState();
        workUnitState.addAll(props);
        this.converter.init(workUnitState);
    };

    @Test(dataProvider = "converterSamples")
    public void testCheckConvert(String input, TimestampRecord<String> expected) {
        assertTrue(converter.transform(input).equals(expected));
    }
}
