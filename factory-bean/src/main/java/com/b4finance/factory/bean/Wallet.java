package com.b4finance.factory.bean;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Wallet {
    private long totalAmount;
    private final transient ReentrantReadWriteLock stateLock;

    public Wallet() {
        this.totalAmount = 0L;
        this.stateLock = new ReentrantReadWriteLock();
    }

    public void addAmount(final long amount) {
        stateLock.writeLock().lock();
        try {
            this.totalAmount = this.getTotalAmount() + amount;
        } finally {
            stateLock.writeLock().unlock();
        }
    }

    public void spendAmount(final long amount) {
        stateLock.writeLock().lock();
        try {
            this.totalAmount = this.totalAmount - amount;
        } finally {
            stateLock.writeLock().unlock();
        }

    }

    public long getTotalAmount() {
        stateLock.readLock().lock();
        try {
            return this.totalAmount;
        } finally {
            stateLock.readLock().unlock();
        }
    }

    public void setTotalAmount(final long totalAmount) {
        stateLock.writeLock().lock();
        try {
            this.totalAmount = totalAmount;
        } finally {
            stateLock.writeLock().unlock();
        }
    }

    public void clear() {
        stateLock.writeLock().lock();
        try {
            this.totalAmount = 0;
        } finally {
            stateLock.writeLock().unlock();
        }
    }
}
