package ru.rambler.hobgobblin.converter;

import gobblin.configuration.WorkUnitState;
import gobblin.converter.Converter;
import gobblin.converter.DataConversionException;
import gobblin.converter.SchemaConversionException;
import gobblin.converter.SingleRecordIterable;
import gobblin.instrumented.converter.InstrumentedConverter;
import gobblin.util.EmptyIterable;
import gobblin.util.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.typesafe.config.Config;
import ru.rambler.hobgobblin.configuration.ConfigurationKeys;

import java.io.UnsupportedEncodingException;

public abstract class ByteArrayConverter extends InstrumentedConverter<String, String, byte[], TimestampRecord<String>> {
    private static final Logger LOG = LoggerFactory.getLogger(ByteArrayConverter.class);
    private String encoding;

    @Override
    public Converter<String, String, byte[], TimestampRecord<String>> init(WorkUnitState workUnit) {
        Config config = ConfigUtils.propertiesToConfig(workUnit.getProperties());
        encoding = ConfigUtils.getString(config, ConfigurationKeys.BYTE_ARRAY_CONVERTER_ENCODING_KEY,
                ConfigurationKeys.DEFAULT_CHARSET_ENCODING);
        return super.init(workUnit);
    }

    @Override
    public String convertSchema(String inputSchema, WorkUnitState workUnitState) throws SchemaConversionException {
        return inputSchema;
    }

    public abstract TimestampRecord<String> transform(String record);

    @Override
    public Iterable<TimestampRecord<String>> convertRecordImpl(String inputSchema, byte[] bytes, WorkUnitState workUnitState)
            throws DataConversionException {
        String payloadString;
        try {
            payloadString = new String(bytes, encoding);
        }
        catch (UnsupportedEncodingException e) {
            LOG.error("Encoding error - non '{}' byte array", encoding, e);
            onException(new DataConversionException(e));
            return new EmptyIterable<>();
        };

        try {
            return new SingleRecordIterable<>(transform(payloadString));
        } catch (RuntimeException e) {
            LOG.error("Convertation error for message '{}'", payloadString, e);
            // popup exception for metrics
            onException(new DataConversionException(e));
            // move broken records to 1970-01-01 folder
            return new SingleRecordIterable<>(new TimestampRecord<>(payloadString, 0));
        }
    }
}
