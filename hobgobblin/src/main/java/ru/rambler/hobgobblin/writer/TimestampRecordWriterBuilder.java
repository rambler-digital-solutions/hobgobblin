package ru.rambler.hobgobblin.writer;

import gobblin.writer.DataWriter;
import gobblin.writer.FsDataWriterBuilder;
import ru.rambler.hobgobblin.converter.TimestampRecord;

import java.io.IOException;

public class TimestampRecordWriterBuilder extends FsDataWriterBuilder<String, TimestampRecord<String>>{
    @Override
    public DataWriter<TimestampRecord<String>> build() throws IOException {
        return new TimestampRecordWriter(this, this.destination.getProperties());
    }
}
