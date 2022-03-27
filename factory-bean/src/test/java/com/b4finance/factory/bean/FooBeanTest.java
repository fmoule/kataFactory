package com.b4finance.factory.bean;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class FooBeanTest {


    @Test
    public void shouldBeEquals() {
        final UUID uuid = randomUUID();
        final FooBean fooBean = new FooBean(uuid);
        assertThat(fooBean).isEqualTo(new FooBean(uuid));
        assertThat(fooBean.hashCode()).isEqualTo(new FooBean(uuid).hashCode());
        assertThat(fooBean).isNotEqualTo(new FooBean(randomUUID()));
        assertThat(fooBean).isNotEqualTo(null);
    }

}