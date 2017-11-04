package ru.rambler.hobgobblin.partitioner;

import gobblin.configuration.State;
import gobblin.writer.partitioner.TimeBasedWriterPartitioner;

import java.nio.charset.Charset;

abstract class SimpleTimestampPartitioner extends TimeBasedWriterPartitioner<byte[]> {
    public static final Charset DEFAULT_CHARSET_ENCODING = Charset.forName("UTF-8");

    public SimpleTimestampPartitioner(State state, int numBranches, int branchId) {
        super(state, numBranches, branchId);
    }

    public abstract long getRecordTimestamp(String s);

    @Override
    public long getRecordTimestamp(byte[] bytes) {
        return getRecordTimestamp(new String(bytes, DEFAULT_CHARSET_ENCODING));
    }
}
