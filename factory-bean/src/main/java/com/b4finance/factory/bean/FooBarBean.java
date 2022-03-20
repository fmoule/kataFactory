package com.b4finance.factory.bean;

import org.apache.commons.lang3.tuple.Pair;

public class FooBarBean extends AbstractBean<Pair<FooBean, BarBean>> {

    public FooBarBean(final FooBean fooBean, final BarBean barBean) {
        super(Pair.of(fooBean, barBean));
    }
}
