package ru.rambler.hobgobblin.converter;

public class TimestampRecord<R> {
    private R record;
    private long timestamp;

    public TimestampRecord(R record, long timestamp) {
        this.record = record;
        this.timestamp = timestamp;
    }

    public R getRecord() {
        return record;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimestampRecord<?> that = (TimestampRecord<?>) o;

        if (timestamp != that.timestamp) return false;
        return record.equals(that.record);

    }

    /**
     * Hack for {@link gobblin.qualitychecker.row.RowLevelErrFileWriter},
     * it write error row to file via standard Object.toString()
     */
    @Override
    public String toString() {
        return this.getRecord() + "\n";
    }

    @Override
    public int hashCode() {
        int result = record.hashCode();
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }
}
