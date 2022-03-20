package com.b4finance.factory.utils;

import org.junit.jupiter.api.Test;

import static com.b4finance.factory.utils.NumberUtils.longPart;
import static org.assertj.core.api.Assertions.assertThat;

class NumberUtilsTest {

    @Test
    public void shouldGetLongPart() {
        assertThat(longPart(2.5)).isEqualTo(2L);
        assertThat(longPart(-3.7)).isEqualTo(-4L);
    }

}