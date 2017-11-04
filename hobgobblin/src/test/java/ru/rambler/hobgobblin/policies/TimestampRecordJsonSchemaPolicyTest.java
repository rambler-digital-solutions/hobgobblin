package ru.rambler.hobgobblin.policies;

import com.google.common.io.Closer;

import gobblin.configuration.State;
import gobblin.qualitychecker.row.RowLevelPolicy;
import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class TimestampRecordJsonSchemaPolicyTest extends Assert {

    public static String loadResource(String name) {
        URL url = Thread.currentThread().getContextClassLoader().getResource("policies/jsonschema/" + name);

        String result = null;

        if(url == null) {
            throw new RuntimeException("Invalid test resource");
        } else {
            Closer closer = Closer.create();

            try {
                InputStream in = closer.register(url.openStream());
                StringWriter writer = new StringWriter();
                IOUtils.copy(in, writer, StandardCharsets.UTF_8);
                result = writer.toString();
                closer.close();
            } catch (IOException e) {
                throw new RuntimeException("Fail to load resource");
            }
            return result;

        }
    }

    @DataProvider
    public Object[][] schemaSamples() {
        return new Object[][] {
                {loadResource("testSample1Valid.json"), loadResource("testSchema1.json"), true},
                {loadResource("testSample1Invalid.json"), loadResource("testSchema1.json"), false},
                {loadResource("testSample1Invalid2.json"), loadResource("testSchema1.json"), false},
                {"not a json", loadResource("testSchema1.json"), false},
        };
    }

    private static JsonSchemaPolicy makeChecker(String schema) {
        Properties props = new Properties();
        props.setProperty(JsonSchemaPolicy.SCHEMA, schema);
        State state = new State(props);
        return new JsonSchemaPolicy(state, RowLevelPolicy.Type.OPTIONAL);
    }

    @Test(dataProvider = "schemaSamples")
    public void testCheckSchemas(String sample, String schema, boolean is_valid) {
        JsonSchemaPolicy checker = makeChecker(schema);
        assertEquals(checker.validate(sample), is_valid);
    }

    @Test
    public void testThrowOnInvalidSchema() {
        try {
            makeChecker("{654}");
            fail();
        } catch (RuntimeException e) {
            return;
        }
    }
}
