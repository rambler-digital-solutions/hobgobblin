package ru.rambler.hobgobblin.partitioner;

import gobblin.configuration.State;
import org.joda.time.DateTime;

import ru.rambler.hobgobblin.partitioner.MinutesBucketTSRecordPartitioner;
import ru.rambler.hobgobblin.converter.TimestampRecord;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Properties;

public class MinutesBucketTSPartitionerTest extends Assert {
    @DataProvider
    public Object[][] samplesNmin() {
        return new Object[][] {
                {new DateTime(2017, 5, 3, 5, 53, 8), new DateTime(2017, 5, 3, 5, 45, 0), new Integer(15)},
                {new DateTime(2015, 2, 1, 18, 5, 28), new DateTime(2015, 2, 1, 18, 0, 0), new Integer(15)},
                {new DateTime(2015, 2, 1, 18, 15, 28), new DateTime(2015, 2, 1, 18, 10, 0), new Integer(10)},
                {new DateTime(2015, 2, 1, 18, 5, 0), new DateTime(2015, 2, 1, 18, 5, 0), new Integer(5)},
        };
    }

    static MinutesBucketTSRecordPartitioner makePartitioner(int bucketInMins) {
        Properties props = new Properties();
        props.setProperty(MinutesBucketTSRecordPartitioner.WRITER_PARTITION_BUCKET_MIN,
                Integer.toString(bucketInMins));
        State state = new State(props);
        return new MinutesBucketTSRecordPartitioner(state, 1, 1);
    }

    @Test(dataProvider = "samplesNmin")
    static void testBucketByNmin(DateTime input, DateTime expect, Integer bucketSize) {
        MinutesBucketTSRecordPartitioner bucketizer = makePartitioner(bucketSize.intValue());
        TimestampRecord<String> tsRec = new TimestampRecord<>("", input.getMillis());
        assertEquals(bucketizer.getRecordTimestamp(tsRec), expect.getMillis());
    }
}
