package ru.rambler.hobgobblin.converter;

import java.util.Iterator;
import java.util.Properties;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import gobblin.configuration.WorkUnitState;
import gobblin.converter.DataConversionException;


@Test
public class ByteArraySamplingConverterTest {

    private void assertNear(int actual, int expected, int threshold, String context) {
        boolean near = (Math.abs(actual - expected) <= Math.abs(threshold));
        Assert.assertTrue(near, context + ": Failed nearness test between "
                + actual + " and " + expected + " with threshold " + threshold);
    }

    public void testSampling()
            throws DataConversionException {

        int numIterations = 100;
        Random random = new Random();
        for (int j = 0; j < numIterations; ++j) {

            ByteArraySamplingConverter sampler = new ByteArraySamplingConverter();
            Properties props = new Properties();
            float randomSampling = random.nextFloat();
            props.setProperty(SamplingConverter.SAMPLE_RATIO_KEY, "" + randomSampling);
            WorkUnitState workUnitState = new WorkUnitState();
            workUnitState.addAll(props);

            sampler.init(workUnitState);

            int numRecords = 10000; // need at least 10k samples
            int sampledRecords = 0;
            for (int i = 0; i < numRecords; ++i) {
                byte[] bytes = new byte[]{(byte)i};
                Iterator<byte[]> recordIter = sampler.convertRecord(null, bytes, workUnitState).iterator();
                if (recordIter.hasNext()) {
                    ++sampledRecords;
                    // make sure we got back the same record
                    Assert.assertEquals(recordIter.next(), bytes, "Sampler should return the same record");
                    Assert.assertFalse(recordIter.hasNext(), "There should only be 1 record returned");
                }
            }
            int threshold = (int) (0.02 * (double) numRecords); // 2 %
            assertNear(sampledRecords, (int) (randomSampling * (float) numRecords), threshold, "SampleRatio: " + randomSampling);
        }
    }

    public void testPassAllByDefault() throws DataConversionException {
        ByteArraySamplingConverter sampler = new ByteArraySamplingConverter();
        Properties props = new Properties();
        WorkUnitState workUnitState = new WorkUnitState();
        workUnitState.addAll(props);

        sampler.init(workUnitState);

        int numRecords = 1000;
        int sampledRecords = 0;
        for (int i = 0; i < numRecords; ++i) {
            byte[] bytes = new byte[]{(byte)i};
            Iterator<byte[]> recordIter = sampler.convertRecord(null, bytes, workUnitState).iterator();
            if (recordIter.hasNext()) {
                ++sampledRecords;
                // make sure we got back the same record
                Assert.assertEquals(recordIter.next(), bytes, "Sampler should return the same record");
                Assert.assertFalse(recordIter.hasNext(), "There should only be 1 record returned");
            }
        }

        Assert.assertEquals(numRecords, sampledRecords);
    }

    @DataProvider
    public Object[][] nonSamplingKeyValues() {
        return new Object[][] {{"non_float"}, {"-0.5"}};
    }

    @Test(dataProvider = "nonSamplingKeyValues",
            expectedExceptions = {IllegalStateException.class, com.typesafe.config.ConfigException.WrongType.class})
    public void testInvalidSampleKey(String key) throws DataConversionException {
        ByteArraySamplingConverter sampler = new ByteArraySamplingConverter();
        Properties props = new Properties();
        WorkUnitState workUnitState = new WorkUnitState();
        props.setProperty(SamplingConverter.SAMPLE_RATIO_KEY, key);
        workUnitState.addAll(props);

        sampler.init(workUnitState);
    }

}
