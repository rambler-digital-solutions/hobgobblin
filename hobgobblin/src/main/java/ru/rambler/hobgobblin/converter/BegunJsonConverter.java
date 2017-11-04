package ru.rambler.hobgobblin.converter;

import org.json.JSONObject;

public class BegunJsonConverter extends UnixTimestampExtractConverter {
    public JSONObject parseFields(String kv_string) {
        JSONObject json_obj = new JSONObject();
        String[] kv_pairs = kv_string.split("\t");

        for (String pair : kv_pairs) {
            String[] pair_splitted = pair.split("=", 2);
            String key = pair_splitted[0];
            String value = pair_splitted.length > 1 ? pair_splitted[1] : "";
            json_obj.put(key, value);
        }
        return json_obj;
    }

    @Override
    public TimestampRecord<String> transform(String record) {
        String[] payloadParts = record.split("\t", 2);
        long syslog_ts = Long.parseLong(payloadParts[0].substring(payloadParts[0].indexOf('@') + 1)) * 1000;
        long timestamp = syslog_ts;

        JSONObject json_obj = parseFields(payloadParts[1]);
        if (json_obj.has(this.getTimestampField())) {
            timestamp = castTimestampToMilliseconds(json_obj.getLong(this.getTimestampField()));
        }

        json_obj.put("ts", syslog_ts);
        return new TimestampRecord<>(json_obj.toString(), timestamp);
    }

}

