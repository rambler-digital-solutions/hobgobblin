package ru.rambler.hobgobblin.source;

import com.google.common.collect.Lists;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class FilteredTextInputFormat extends TextInputFormat {
    private static final Logger LOG = LoggerFactory.getLogger(FilteredTextInputFormat.class);
    private FileSystem fs;

    public FilteredTextInputFormat() {
    }

    private boolean isDir(InputSplit input, JobContext job) throws IOException {
        Path path = ((FileSplit) input).getPath();
        LOG.info(String.format("InputSplit: %s", input.toString()));
        if (fs == null) {
            fs = path.getFileSystem(job.getConfiguration());
        }
        return fs.isDirectory(path);
    }

    @Override
    public List<InputSplit> getSplits(JobContext job) throws IOException {
        setInputDirRecursive((Job) job, true);

        List<InputSplit> result = Lists.newArrayList();
        Iterator<InputSplit> iterator = super.getSplits(job).iterator();

        while (iterator.hasNext()) {
            InputSplit item = iterator.next();
            if (!isDir(item, job)) {
                result.add(item);
            }
        }
        return result;
    }
}