package com.b4finance.back.robot;

import com.b4finance.back.robot.actions.*;
import com.b4finance.factory.bean.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RobotManager implements Startable {
    private final Warehouse<FooBean> fooWarehouse;
    private final Warehouse<BarBean> barWarehouse;
    private final Warehouse<FooBarBean> fooBarWarehouse;
    private final Wallet wallet;
    private final List<Robot> robots;
    private ExecutorService executorService;
    private List<RobotAction> defaultRobotActions;
    private final transient ReentrantReadWriteLock robotlock;
    private final transient ReentrantReadWriteLock stateLock;
    private transient boolean isStarted;

    ///// Constructeurs :

    public RobotManager(final Warehouse<FooBean> fooWarehouse,
                        final Warehouse<BarBean> barWarehouse,
                        final Warehouse<FooBarBean> fooBarWarehouse) {
        this.fooWarehouse = fooWarehouse;
        this.barWarehouse = barWarehouse;
        this.fooBarWarehouse = fooBarWarehouse;
        this.wallet = new Wallet();
        this.robots = new ArrayList<>();
        this.robotlock = new ReentrantReadWriteLock();
        this.stateLock = new ReentrantReadWriteLock();
        this.isStarted = false;
    }

    public RobotManager() {
        this(new Warehouse<>(), new Warehouse<>(), new Warehouse<>());
    }

    ///// Méthodes générales :

    public BarBean fetchBarBean() {
        return getBarWarehouse().fetchbean();
    }

    public void putBarBean(final BarBean barBean) {
        this.barWarehouse.put(barBean);
    }

    public FooBean fetchFooBean() {
        return this.fooWarehouse.fetchbean();
    }

    public void putFooBean(final FooBean fooBean) {
        this.fooWarehouse.put(fooBean);
    }

    public void putFoobarBean(final FooBarBean fooBarBean) {
        this.fooBarWarehouse.put(fooBarBean);
    }

    public void addAmount(int totalNbFoobars) {
        this.wallet.addAmount(totalNbFoobars);
    }

    public void createNewRobot(final List<RobotAction> actions) {
        final Robot createdRobot;
        this.robotlock.writeLock().lock();
        try {
            createdRobot = new Robot("robot" + this.robots.size(), this);
            createdRobot.setActions(actions);
            this.robots.add(createdRobot);
        } finally {
            this.robotlock.writeLock().unlock();
        }
        if (this.isStarted()) {
            createdRobot.start();
        }
    }

    @Override
    public void start() {
        for (Robot robot : this.getRobots()) {
            robot.start();
        }
        setStarted(true);
    }

    @Override
    public void stop() {
        setStarted(false);
        for (Robot robot : this.getRobots()) {
            robot.stop();
        }
        this.executorService.shutdownNow();
        this.clear();
    }

    public void clear() {
        this.robotlock.writeLock().lock();
        try {
            this.robots.clear();
        } finally {
            this.robotlock.writeLock().unlock();
        }
    }

    @Override
    public boolean isStarted() {
        this.stateLock.readLock().lock();
        try {
            return isStarted;
        } finally {
            this.stateLock.readLock().unlock();
        }
    }

    ///// Getters & Setters :

    private void setStarted(final boolean isStartedValue) {
        this.stateLock.writeLock().lock();
        try {
            this.isStarted = isStartedValue;
        } finally {
            this.stateLock.writeLock().unlock();
        }
    }

    public Warehouse<FooBean> getFooWarehouse() {
        return fooWarehouse;
    }

    public Warehouse<BarBean> getBarWarehouse() {
        return barWarehouse;
    }

    public Warehouse<FooBarBean> getFooBarWarehouse() {
        return fooBarWarehouse;
    }

    public int getNbFoobars() {
        return this.fooBarWarehouse.size();
    }

    public int getNbFoos() {
        return this.fooWarehouse.size();
    }

    public int getNbBars() {
        return this.barWarehouse.size();
    }

    public long getTotalAmount() {
        return this.getWallet().getTotalAmount();
    }

    public List<Robot> getRobots() {
        this.robotlock.readLock().lock();
        try {
            return this.robots;
        } finally {
            this.robotlock.readLock().unlock();
        }
    }

    public Wallet getWallet() {
        return wallet;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public void setExecutorService(final ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setDefaultRobotActions(final List<RobotAction> defaultRobotActions) {
        this.defaultRobotActions = defaultRobotActions;
    }

    public List<RobotAction> getDefaultRobotActions() {
        return defaultRobotActions;
    }
}
