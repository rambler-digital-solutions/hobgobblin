package ru.rambler.hobgobblin.utils;

import java.util.Arrays;
import org.apache.commons.codec.binary.Base64;

public class RUID {
    private byte[] ruidBytes;
    private String representation;

    public static final int LENGTH = 16;

    public static class RuidConversionException extends RuntimeException {
        public RuidConversionException(String message) {
            super(message);
        }
    }

    public RUID(byte[] ruidBytes) {
        if (ruidBytes.length != LENGTH) {
            throw new RuidConversionException("Invalid byte array size for RUID");
        }
        this.ruidBytes = Arrays.copyOf(ruidBytes, LENGTH);
    }

    public static RUID fromBase64(String ruid_b64) {
        byte[] decodedData = Base64.decodeBase64(ruid_b64);
        if (decodedData.length < LENGTH) {
            throw new RuidConversionException("Invalid message size for RUID");
        }
        byte[] ruidBytes = new byte[LENGTH];

        for (int i = 0; i < LENGTH; i++) {
            int chunk = i/4;
            int offset = i%4;
            int dst = chunk*4 + (3-offset);
            ruidBytes[dst] = decodedData[i];
        }
        return new RUID(ruidBytes);
    }

    @Override
    public String toString() {
        if (this.representation == null) {
            StringBuilder ruid_builder = new StringBuilder();
            for (int i = 0; i < LENGTH; i++) {
                ruid_builder.append(String.format("%02X", ruidBytes[i]));
            }
            this.representation = ruid_builder.toString();
        }
        return this.representation;
    }
}
