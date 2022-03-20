package com.b4finance.factory.bean;

import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class FooBarBeanTest {

    ///// Tests unitaires :

    @Test
    public void shouldBeEquals() {
        final FooBean fooBean = new FooBean();
        final BarBean barBean = new BarBean();
        final FooBarBean fooBarBean = new FooBarBean(fooBean, barBean);
        assertThat(fooBarBean).isEqualTo(new FooBarBean(fooBean, barBean));
        assertThat(fooBarBean.hashCode()).isEqualTo(new FooBarBean(fooBean, barBean).hashCode());
        assertThat(fooBarBean).isNotEqualTo(new FooBarBean(new FooBean(randomUUID()), barBean));
        assertThat(fooBarBean).isNotEqualTo(new FooBarBean(fooBean, new BarBean(randomUUID())));
        assertThat(fooBarBean).isNotEqualTo(new FooBarBean(new FooBean(randomUUID()), new BarBean(randomUUID())));
        assertThat(fooBarBean).isNotEqualTo(null);
    }

}