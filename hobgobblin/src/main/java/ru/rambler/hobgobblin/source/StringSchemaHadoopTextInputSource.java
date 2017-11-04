package ru.rambler.hobgobblin.source;

import gobblin.configuration.State;
import gobblin.configuration.WorkUnitState;
import gobblin.source.extractor.hadoop.HadoopFileInputExtractor;

import gobblin.source.extractor.hadoop.HadoopFileInputSource;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class StringSchemaHadoopTextInputSource extends HadoopFileInputSource<String, Text, LongWritable, Text> {
    private class StringSchemaHadoopFileInputExtractor extends HadoopFileInputExtractor<String, Text, LongWritable, Text> {
        public StringSchemaHadoopFileInputExtractor(RecordReader<LongWritable, Text> recordReader, boolean readKeys) {
            super(recordReader, readKeys);
        }

        @Override
        public String getSchema() throws IOException {
            return "";
        }
    }

    @Override
    protected FileInputFormat<LongWritable, Text> getFileInputFormat(State state, Configuration configuration) {
        return new FilteredTextInputFormat();
    }

    @Override
    protected HadoopFileInputExtractor<String, Text, LongWritable, Text> getExtractor(
            WorkUnitState workUnitState, RecordReader<LongWritable, Text> recordReader,
            FileSplit fileSplit, boolean readKeys) {
        return new StringSchemaHadoopFileInputExtractor(recordReader, readKeys);
    }
}
