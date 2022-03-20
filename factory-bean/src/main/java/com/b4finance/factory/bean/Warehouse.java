package com.b4finance.factory.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Warehouse<T> {
    private final Set<T> beans;
    private final transient ReentrantReadWriteLock lock;

    public Warehouse() {
        this.beans = new HashSet<>();
        this.lock = new ReentrantReadWriteLock();
    }

    ///// Méthodes générales :

    public int size() {
        lock.readLock().lock();
        try {
            return this.beans.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void clear() {
        this.lock.writeLock().lock();
        try {
            this.beans.clear();
        } finally {
            this.lock.writeLock().unlock();
        }
    }


    public void put(final T bean) {
        lock.writeLock().lock();
        try {
            this.beans.add(bean);
        } finally {
            lock.writeLock().unlock();
        }
    }


    public T fetchbean() {
        lock.writeLock().lock();
        try {
            final T bean = this.beans.stream().findFirst().orElse(null);
            this.beans.remove(bean);
            return bean;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<T> fetchBeans(int totalNbFoobars) {
        final List<T> beans = new ArrayList<>();
        lock.writeLock().lock();
        try {
            int cursor = 0;
            for (T bean : new ArrayList<>(this.beans)) {
                if (cursor >= (totalNbFoobars)) {
                    return beans;
                }
                this.beans.remove(bean);
                beans.add(bean);
                cursor++;
            }
            return beans;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
