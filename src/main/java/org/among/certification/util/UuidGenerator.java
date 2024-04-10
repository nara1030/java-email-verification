package org.among.certification.util;

import java.util.UUID;

public class UuidGenerator {
    public static String uuidGenerator() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
