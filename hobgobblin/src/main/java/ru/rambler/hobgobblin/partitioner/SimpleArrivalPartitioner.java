package ru.rambler.hobgobblin.partitioner;

import gobblin.configuration.State;

public class SimpleArrivalPartitioner extends SimpleTimestampPartitioner {
    public SimpleArrivalPartitioner(State state, int numBranches, int branchId) {
        super(state, numBranches, branchId);
    }

    @Override
    public long getRecordTimestamp(String s) { return 0; }

    @Override
    public long getRecordTimestamp(byte[] bytes) {
        return System.currentTimeMillis();
    }
}
