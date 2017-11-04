package ru.rambler.hobgobblin.converter;

import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BegunJsonConverterTest extends TimestampExractConverterTest {

    public TimestampExtractConverter newConverter(){
        return new BegunJsonConverter();
    }

    @DataProvider
    public Object[][] converterSamples() {
        String sample = "@1496235381\tclick_ts=1496235388\taccount_id=456183692\theight=600" +
                "\tssp_bid=323606415" +
                "\tbid_id=d06a0fa649aecedc\tcreative_id=456184122\tcreative_template_id=441385960" +
                "\truid=000000465889E33811800B214026C601\tcampaign_id=456183694\tbid_ts=1496235330";
        return new Object[][] {
                {sample, 1496235381000L, "ts", "seconds"},
                {sample, 1496235388000L, "click_ts", "seconds"},
                {sample, 1496235330L, "bid_ts", "milliseconds"},
        };
    }

    @DataProvider
    public Object[][] parseKVSamples() {
        String sample = "channel=dsp_clicks\tbillable=0\tclick_ts=1497985909\tserver_oid=1015825\t" +
                "url=http://ad.adriver.ru/cgi-bin/click.cgi?sid=1&bt=21&ad=627474&pid=2566898&bid=5068889&bn=5068889&rnd=1040038842\t" +
                "tagid=432185890\twidth=0\tbid_id=1ed098fd70566d93\tcreative_id=455513204";
        return new Object[][] {
                {sample, "url", "http://ad.adriver.ru/cgi-bin/click.cgi?sid=1&bt=21&ad=627474&pid=2566898&bid=5068889&bn=5068889&rnd=1040038842"},
                {sample, "channel", "dsp_clicks"},
                {sample, "bid_id", "1ed098fd70566d93"},
                {sample, "server_oid", "1015825"},
        };
    }

    @Test(dataProvider = "parseKVSamples")
    public void testParseFields(String input_kv, String expect_key, String expect_value) {
        BegunJsonConverter converter = new BegunJsonConverter();
        JSONObject json = converter.parseFields(input_kv);
        assertTrue(json.get(expect_key).equals(expect_value));
    }
}
