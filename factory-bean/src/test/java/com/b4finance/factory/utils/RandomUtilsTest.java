package com.b4finance.factory.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

class RandomUtilsTest {


    @Test
    public void shouldCreateRandom() {
        BigDecimal random;
        for (int i = 0; i < 10; i++) {
            random = RandomUtils.random();
            assertThat(random).isNotNull();
            assertThat(random.compareTo(ZERO)).isGreaterThanOrEqualTo(0);
            assertThat(random.compareTo(ONE)).isLessThanOrEqualTo(0);
        }
    }


    @Test
    public void shouldCReateRandomInInterval() {
        BigDecimal random;
        for (int i = 0; i < 10; i++) {
            random = RandomUtils.random(1, 5);
            assertThat(random).isNotNull();
            assertThat(random.compareTo(ONE)).isGreaterThanOrEqualTo(0);
            assertThat(random.compareTo(new BigDecimal("5"))).isLessThanOrEqualTo(0);
        }
    }

}