package com.b4finance.factory.utils;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class NumberUtils {

    private NumberUtils() {
        // EMPTY
    }

    public static long longPart(final Number number) {
        if (number == null) {
            return 0L;
        }
        final BigDecimal bgValue = new BigDecimal(number.toString());
        if (bgValue.compareTo(ZERO) >= 0) {
            return bgValue.longValue();
        } else {
            return bgValue.longValue() - 1;
        }
    }
}
