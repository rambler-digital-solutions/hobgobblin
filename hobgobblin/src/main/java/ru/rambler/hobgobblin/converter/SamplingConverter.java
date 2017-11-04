package ru.rambler.hobgobblin.converter;

import com.google.common.base.Preconditions;
import com.typesafe.config.Config;
import gobblin.configuration.WorkUnitState;
import gobblin.converter.Converter;
import gobblin.converter.DataConversionException;
import gobblin.converter.SchemaConversionException;
import gobblin.converter.SingleRecordIterable;
import gobblin.util.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Random;


/**
 * A converter that samples records based on a configured sampling ratio.
 * Based on original Sampling Converter in Gobblin but supported primitives as byte[]
 */

public abstract class SamplingConverter<S, D> extends Converter<S, S, D, D> {
    private static final Logger LOG = LoggerFactory.getLogger(SamplingConverter.class);
    public static final String SAMPLE_RATIO_KEY="converter.sample.ratio";
    public static final double DEFAULT_SAMPLE_RATIO=1.0; // Sample 100% by default

    private final Random random = new Random();
    private double sampleRatio = DEFAULT_SAMPLE_RATIO;

    @Override
    public Converter<S, S, D, D> init(WorkUnitState workUnit) {
        super.init(workUnit);

        Config config = ConfigUtils.propertiesToConfig(workUnit.getProperties());
        double sampleRatio = ConfigUtils.getDouble(config, SAMPLE_RATIO_KEY, DEFAULT_SAMPLE_RATIO);
        Preconditions.checkState(sampleRatio >= 0 && sampleRatio <= 1.0,
                "Sample ratio must be between 0.0 and 1.0. Found " + sampleRatio);

        this.sampleRatio = sampleRatio;
        LOG.debug("Sample ratio configured: {}", this.sampleRatio);
        return this;
    }

    @Override
    public S convertSchema(S inputSchema, WorkUnitState workUnit) throws SchemaConversionException {
        return inputSchema;
    }

    @Override
    public Iterable<D> convertRecord(S outputSchema, D inputRecord, WorkUnitState workUnit)
            throws DataConversionException {
        if (random.nextDouble() <= this.sampleRatio) {
            return new SingleRecordIterable<>(inputRecord);
        } else {
            return Collections.EMPTY_LIST;
        }
    }
}
