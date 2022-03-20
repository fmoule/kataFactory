package com.b4finance.factory.bean;

import java.util.Objects;

public abstract class AbstractBean<T> {
    private final T id;

    public AbstractBean(final T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (this.getClass() != obj.getClass())) {
            return false;
        }
        return Objects.equals(this.id, ((AbstractBean<?>) obj).id);
    }
}
