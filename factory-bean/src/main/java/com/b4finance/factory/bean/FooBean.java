package com.b4finance.factory.bean;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public class FooBean extends AbstractBean<UUID> {

    public FooBean() {
        super(randomUUID());
    }

    public FooBean(final UUID id) {
        super(id);
    }
}
