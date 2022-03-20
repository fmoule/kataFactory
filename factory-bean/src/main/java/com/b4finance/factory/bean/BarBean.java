package com.b4finance.factory.bean;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public class BarBean extends AbstractBean<UUID> {

    public BarBean() {
        super(randomUUID());
    }

    public BarBean(final UUID id) {
        super(id);
    }
}
