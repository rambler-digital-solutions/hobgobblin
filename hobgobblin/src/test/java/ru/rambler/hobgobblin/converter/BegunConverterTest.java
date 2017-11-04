package ru.rambler.hobgobblin.converter;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BegunConverterTest extends ConverterAssert {

    @DataProvider
    @Override
    public Object[][] converterSamples() {
        String sample1 =
            "@1490960957  channel=bacon_logs      responseid=118D58695C75AB00     ruid=000022D458C3B5530B423C742D3E7201" +
            "   app_id=822295a4-ee11-4482-92a5-3d6e3d67e8b2     ts=1490960957" +
            "   referer=http://fishki.net/2255201-takoj-dizajn-opasen-dlja-zhizni-i-zdorovyja.html" +
            "   xff=10.15.3.53  xri=85.21.43.180" +
            "   ua=Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 YaBrowser/17.3.1.840 Yowser/2.5 Safari/537.36" +
            "   answer={\"data\":[{\"id\":\"1\",\"segment\":[{\"id\":7687,\"lm\":1490883474},{\"id\":151,\"lm\":1490876399}," +
            "{\"id\":158,\"lm\":1490876399},{\"id\":7762,\"lm\":1490871679},{\"id\":13,\"lm\":1490821200,\"prob\":0.38},{\"id\":9,\"lm\":1490821200,\"prob\":0.34},"+
            "{\"id\":7629,\"lm\":1490800955},{\"id\":7685,\"lm\":1490800955},{\"id\":7630,\"lm\":1490800955},{\"id\":138,\"lm\":1490800690}," +
            "{\"id\":137,\"lm\":1490800690},{\"id\":66,\"lm\":1490797972},{\"id\":7711,\"lm\":1490795436},{\"id\":7649,\"lm\":1490795131}," +
            "{\"id\":7638,\"lm\":1490795131},{\"id\":6,\"lm\":1490734800,\"prob\":0.7},{\"id\":110,\"lm\":1490710856},{\"id\":120,\"lm\":1490710856}," +
            "{\"id\":7780,\"lm\":1490710856},{\"id\":148,\"lm\":1490256005},{\"id\":162,\"lm\":1490256005},{\"id\":70,\"lm\":1490008726}," +
            "{\"id\":154,\"lm\":1489415580},{\"id\":34,\"lm\":1489415580}]}]}";
        return new Object[][]{
            {sample1, new TimestampRecord<String>(sample1, 1490960957000L) },
        };
    }

    @BeforeMethod
    public void setUp() throws Exception {
        this.converter = new BegunConverter();
        initConverter();
    }
}
