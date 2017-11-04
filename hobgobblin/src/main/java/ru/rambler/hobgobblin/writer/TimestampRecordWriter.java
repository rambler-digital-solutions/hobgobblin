package ru.rambler.hobgobblin.writer;

import ru.rambler.hobgobblin.configuration.ConfigurationKeys;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import gobblin.configuration.State;
import gobblin.writer.FsDataWriter;
import gobblin.writer.FsDataWriterBuilder;
import ru.rambler.hobgobblin.converter.TimestampRecord;

import java.io.IOException;
import java.io.OutputStream;


public class TimestampRecordWriter extends FsDataWriter<TimestampRecord<String>> {
    private final Optional<Byte> recordDelimiter; // optional byte to place between each record write
    private int recordsWritten;
    private int bytesWritten;
    private String encoding;

    private final OutputStream stagingFileOutputStream;

    public TimestampRecordWriter(FsDataWriterBuilder<?, TimestampRecord<String>> builder, State properties)
            throws IOException {
        super(builder, properties);
        String delim;
        if ((delim = properties.getProp(ConfigurationKeys.STRING_WRITER_DELIMITER, null)) == null || delim.length() == 0) {
            this.recordDelimiter = Optional.absent();
        } else {
            this.recordDelimiter = Optional.of(delim.getBytes(ConfigurationKeys.DEFAULT_CHARSET_ENCODING)[0]);
        }
        this.encoding = properties.getProp(ConfigurationKeys.STRING_WRITER_ENCODING, ConfigurationKeys.DEFAULT_CHARSET_ENCODING);

        this.recordsWritten = 0;
        this.bytesWritten = 0;
        this.stagingFileOutputStream = createStagingFileOutputStream();

        setStagingFileGroup();
    }

    /**
     * Write a source record to the staging file
     *
     * @param record data record to write
     * @throws java.io.IOException if there is anything wrong writing the record
     */
    @Override
    public void write(TimestampRecord<String> record) throws IOException {
        Preconditions.checkNotNull(record);

        byte[] toWrite = record.getRecord().getBytes(this.encoding);
        this.stagingFileOutputStream.write(toWrite);
        this.bytesWritten += toWrite.length;
        if (this.recordDelimiter.isPresent()) {
            this.stagingFileOutputStream.write(this.recordDelimiter.get());
            this.bytesWritten++;
        }
        this.recordsWritten++;
    }

    /**
     * Get the number of records written.
     *
     * @return number of records written
     */
    @Override
    public long recordsWritten() {
        return this.recordsWritten;
    }

    /**
     * Get the number of bytes written.
     *
     * @return number of bytes written
     */
    @Override
    public long bytesWritten() throws IOException {
        return this.bytesWritten;
    }

    @Override
    public boolean isSpeculativeAttemptSafe() {
        return this.writerAttemptIdOptional.isPresent() && this.getClass() == TimestampRecordWriter.class;
    }
}
