package ru.rambler.hobgobblin.policies;

import gobblin.configuration.State;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonSchemaPolicy extends TimestampRecordSchemaPolicy {
    private static final Logger LOG = LoggerFactory.getLogger(JsonSchemaPolicy.class);
    private Schema schema;

    public JsonSchemaPolicy(State state, Type type) {
        super(state, type);
        try {
            this.schema = SchemaLoader.load(new JSONObject(rawSchema));
        } catch (Exception e) {
            LOG.error("Invalid json schema in settings", e);
            throw new RuntimeException("Invalid json schema in settings", e);
        }
    }

    @Override
    public boolean validate(String message) {
        try {
            JSONObject json = new JSONObject(message);
            schema.validate(json);
            return true;
        } catch (JSONException e) {
            LOG.error("Invalid json in message", e);
            return false;
        } catch (ValidationException e) {
            LOG.error("The message does not match the defined scheme", e);
            return false;
        }
    }
}
