package com.example.ewallet.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public final class TransactionCodeGenerator {
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private TransactionCodeGenerator() {
    }

    /**
     * Sinh mã giao dịch mới.
     */
    public static String generate() {
        String timestamp = LocalDateTime.now(ZoneOffset.UTC).format(TIMESTAMP_FORMAT);
        int suffix = ThreadLocalRandom.current().nextInt(0, 1000);
        return String.format("TX%s%03d", timestamp, suffix);
    }
}
