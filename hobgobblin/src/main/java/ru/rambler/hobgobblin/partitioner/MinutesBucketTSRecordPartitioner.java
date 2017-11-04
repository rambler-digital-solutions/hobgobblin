package ru.rambler.hobgobblin.partitioner;

import gobblin.configuration.ConfigurationKeys;
import gobblin.configuration.State;
import gobblin.util.ForkOperatorUtils;
import ru.rambler.hobgobblin.converter.TimestampRecord;

public class MinutesBucketTSRecordPartitioner extends TimestampRecordPartitioner {
    public static final String WRITER_PARTITION_BUCKET_MIN = ConfigurationKeys.WRITER_PREFIX + ".partition.bucket.min";
    private final long bucketInMilisecs;

    public MinutesBucketTSRecordPartitioner(State state, int numBranches, int branchId) {
        super(state, numBranches, branchId);
        this.bucketInMilisecs = getBucketSizeInMins(state, numBranches, branchId) * 60L * 1000L;
    }

    private static long getBucketSizeInMins(State state, int numBranches, int branchId) {
        String propName = ForkOperatorUtils.getPropertyNameForBranch(WRITER_PARTITION_BUCKET_MIN, numBranches, branchId);
        return state.getPropAsInt(propName);
    }

    @Override
    public long getRecordTimestamp(TimestampRecord<String> stringTimestampRecord) {
        long ts = super.getRecordTimestamp(stringTimestampRecord);
        return ts - (ts % this.bucketInMilisecs);
    }
}
