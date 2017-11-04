package ru.rambler.hobgobblin.converter;

import gobblin.configuration.WorkUnitState;
import gobblin.converter.Converter;
import gobblin.converter.DataConversionException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Properties;

@Test
public class ByteArrayConverterTest extends Assert {

    class ThrowableConverter extends ByteArrayConverter {
        @Override
        public TimestampRecord<String> transform(String record) {
            throw new RuntimeException("It's throw!");
        }
    }

    public void testNullTimestampOnTramsformException(){
        Properties props = new Properties();
        WorkUnitState workUnitState = new WorkUnitState();
        workUnitState.addAll(props);
        Converter converter = new ThrowableConverter();
        converter.init(workUnitState);

        try {
            Iterable<TimestampRecord<String>> iterable = converter.convertRecord("", new byte[0], workUnitState);
            assertTrue(iterable.iterator().hasNext());
            assertEquals(iterable.iterator().next().getTimestamp(), 0L);
        } catch (DataConversionException e) {
            fail();
        }

    }
}
