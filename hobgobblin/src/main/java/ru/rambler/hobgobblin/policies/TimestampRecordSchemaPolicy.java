package ru.rambler.hobgobblin.policies;

import gobblin.configuration.State;

import ru.rambler.hobgobblin.converter.TimestampRecord;


public abstract class TimestampRecordSchemaPolicy extends SchemaPolicy {
    public TimestampRecordSchemaPolicy(State state, Type type) {
        super(state, type);
    }

    public abstract boolean validate(String message);

    @Override
    public Result executePolicy(Object record) {
        if (!(record instanceof TimestampRecord)) {
            return Result.FAILED;
        }
        TimestampRecord<String> tsRec = (TimestampRecord) record;
        return validate(tsRec.getRecord()) ? Result.PASSED : Result.FAILED;
    }
}
