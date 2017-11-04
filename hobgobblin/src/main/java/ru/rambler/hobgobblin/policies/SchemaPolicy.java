package ru.rambler.hobgobblin.policies;

import com.typesafe.config.Config;
import gobblin.configuration.State;
import gobblin.qualitychecker.row.RowLevelPolicy;
import gobblin.util.ConfigUtils;


public abstract class SchemaPolicy extends RowLevelPolicy {
    public static final String SCHEMA = "qualitychecker.row.policy.schema";
    public static final String DEFAULT_SCHEMA = null;

    protected String rawSchema;

    public SchemaPolicy(State state, Type type) {
        super(state, type);
        Config config = ConfigUtils.propertiesToConfig(state.getProperties());
        rawSchema = ConfigUtils.getString(config, SCHEMA, DEFAULT_SCHEMA);
    }
}
