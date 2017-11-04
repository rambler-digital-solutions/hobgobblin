package ru.rambler.hobgobblin.partitioner;

import gobblin.configuration.State;
import gobblin.writer.partitioner.TimeBasedWriterPartitioner;
import ru.rambler.hobgobblin.converter.TimestampRecord;

public class TimestampRecordPartitioner extends TimeBasedWriterPartitioner<TimestampRecord<String>> {
    public TimestampRecordPartitioner(State state, int numBranches, int branchId) {
        super(state, numBranches, branchId);
    }

    @Override
    public long getRecordTimestamp(TimestampRecord<String> stringTimestampRecord) {
        return stringTimestampRecord.getTimestamp();
    }
}
