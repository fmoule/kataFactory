package com.b4finance.factory.utils;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;

public class RandomUtils {

    private RandomUtils() {
        // EMPTY
    }

    public static BigDecimal random() {
        //noinspection UnpredictableBigDecimalConstructorCall
        return new BigDecimal(Math.random(), new MathContext(5, HALF_EVEN));
    }

    public static BigDecimal random(final Number min, final Number max) {
        final BigDecimal minValue = (min == null ? ZERO : new BigDecimal(min.toString()));
        final BigDecimal maxValue = (max == null ? ZERO : new BigDecimal(max.toString()));
        if (minValue.compareTo(maxValue) > 0) {
            throw new IllegalArgumentException("La valeur min doit être inférieurs à la valeur max");
        }
        BigDecimal bigDecimal = random();
        bigDecimal = bigDecimal.multiply((maxValue.add(minValue.negate())));
        bigDecimal = bigDecimal.add(minValue);
        return bigDecimal;
    }
}
