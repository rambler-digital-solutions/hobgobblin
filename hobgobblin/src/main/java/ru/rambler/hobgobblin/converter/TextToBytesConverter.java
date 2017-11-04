package ru.rambler.hobgobblin.converter;

import gobblin.configuration.WorkUnitState;
import gobblin.converter.Converter;
import gobblin.converter.DataConversionException;
import gobblin.converter.SchemaConversionException;
import gobblin.converter.SingleRecordIterable;
import org.apache.hadoop.io.Text;

public class TextToBytesConverter extends Converter<String, String, Text, byte[]> {
    @Override
    public String convertSchema(String inputSchema, WorkUnitState workUnit) throws SchemaConversionException {
        return inputSchema;
    }

    @Override
    public Iterable<byte[]> convertRecord(String outputSchema, Text inputRecord, WorkUnitState workUnit) throws DataConversionException {
        return new SingleRecordIterable<>(inputRecord.getBytes());
    }
}
