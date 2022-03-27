package com.b4finance.factory.bean;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class BarBeanTest {


    @Test
    public void shouldBeEquals() {
        final UUID uuid = randomUUID();
        final BarBean barBean = new BarBean(uuid);
        assertThat(barBean).isEqualTo(new BarBean(uuid));
        assertThat(barBean.hashCode()).isEqualTo(new BarBean(uuid).hashCode());
        assertThat(barBean).isNotEqualTo(new BarBean(randomUUID()));
        assertThat(barBean).isNotEqualTo(null);
    }

}