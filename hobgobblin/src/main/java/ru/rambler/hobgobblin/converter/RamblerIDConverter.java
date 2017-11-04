package ru.rambler.hobgobblin.converter;

import org.json.JSONObject;
import ru.rambler.hobgobblin.utils.RUID;


public class RamblerIDConverter extends ByteArrayConverter {
    @Override
    public TimestampRecord<String> transform(String record) {
        JSONObject json_obj = new JSONObject(record);
        long timestamp = json_obj.getLong("timestamp")/1000; // milliseconds
        String ruid_b64 = json_obj.getString("ruid");

        String ruid = "";
        if (ruid_b64.length() != 0) {
            ruid = RUID.fromBase64(ruid_b64).toString();
        }
        json_obj.put("ruid", ruid);
        json_obj.put("ruid_b64", ruid_b64);
        return new TimestampRecord<>(json_obj.toString(), timestamp);
    }
}
