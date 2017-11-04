package ru.rambler.hobgobblin.source;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

import java.util.regex.Pattern;

public class RegexPathFilter implements PathFilter {
    public static final String REGEX = "mapreduce.input.pathFilter.regex";
    public static final String DEFAULT_REGEX = ".*";

    private Pattern pattern;

    public RegexPathFilter(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public RegexPathFilter() {
        this(System.getProperty(REGEX, DEFAULT_REGEX));
    }

    @Override
    public boolean accept(Path path) {
        return this.pattern.matcher(path.getName()).matches();
    }
}
