package ru.rambler.hobgobblin.converter;

import org.apache.commons.lang.StringUtils;
import ru.rambler.hobgobblin.utils.RUID;

public class Top100Converter extends ByteArrayConverter {

    @Override
    public TimestampRecord<String> transform(String record) {
        String[] splittedString = record.split("\t");

        String ruid_b64 = splittedString[9];
        if (ruid_b64.length() > 4) {
            splittedString[9] = RUID.fromBase64(ruid_b64).toString();
        }
        double timestamp_double = Double.parseDouble(splittedString[1]);
        long timestamp = (long)(timestamp_double*1000);
        return new TimestampRecord<>(StringUtils.join(splittedString, "\t"), timestamp);
    }
}
