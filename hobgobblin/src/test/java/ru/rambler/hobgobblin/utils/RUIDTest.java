package ru.rambler.hobgobblin.utils;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.rambler.hobgobblin.utils.RUID;


public class RUIDTest extends Assert {
    @DataProvider
    public Object[][] ruidSamples() {
        return new Object[][] {
                {"RgAAABjq/FfQH1eSAZndcQB=", "0000004657FCEA1892571FD071DD9901"},
                {"vAsAAEdxwVjuIb6RAQ0cAAB=", "00000BBC58C1714791BE21EE001C0D01"},
        };
    }

    @DataProvider
    public Object[][] failSamples() {
        return new Object[][] {
                {"non_base64**"},
                {"SGVsbG8K"}, // non 16 bytes
        };
    }

    @Test(dataProvider = "ruidSamples")
    static void testConversion(String input, String expect) {
        RUID ruid = RUID.fromBase64(input);
        assertEquals(ruid.toString(), expect);
    }

    @Test(dataProvider = "failSamples", expectedExceptions = RUID.RuidConversionException.class)
    static void testFailConversion(String input) {
        RUID ruid = RUID.fromBase64(input);
    }
}
