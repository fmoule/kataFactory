package com.b4finance.back.robot;

import com.b4finance.back.robot.actions.CyclicAction;
import com.b4finance.back.robot.actions.RobotAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Robot implements Startable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Robot.class);
    private final RobotManager robotManager;
    private final transient ReentrantReadWriteLock stateLock;
    private final CyclicAction cyclicAction;
    private final String name;
    private boolean isStarted;


    public Robot(final String robotName, final RobotManager robotManager) {
        this.name = robotName;
        this.robotManager = robotManager;
        this.isStarted = false;
        this.stateLock = new ReentrantReadWriteLock();
        this.cyclicAction = new CyclicAction();
    }

    ///// Méthodes de la classe Object :

    @Override
    public int hashCode() {
        return Objects.hashCode(this.name);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (this.getClass() != obj.getClass())) {
            return false;
        }
        return Objects.equals(this.name, ((Robot) obj).name);
    }


    ///// Méthodes de l'interface Startable :

    @Override
    public void start() {
        this.setStarted(true);
        this.robotManager.getExecutorService().submit(new RobotRunnable(this, this.cyclicAction));
    }

    @Override
    public void stop() {
        this.setStarted(false);
    }


    ///// Getters :

    public RobotManager getRobotManager() {
        return robotManager;
    }

    public void setStarted(final boolean isStarted) {
        stateLock.writeLock().lock();
        try {
            this.isStarted = isStarted;
        } finally {
            stateLock.writeLock().unlock();
        }
    }

    public boolean isStarted() {
        stateLock.readLock().lock();
        try {
            return isStarted;
        } finally {
            stateLock.readLock().unlock();
        }
    }

    public void setActions(final List<RobotAction> robotActions) {
        cyclicAction.setActions(robotActions);
    }

    public String getCurrentActionName() {
        return this.cyclicAction.getName();
    }

    public String getName() {
        return name;
    }

    ///// Classe(s) interne(s):

    private static class RobotRunnable implements Runnable {
        private final Robot robot;
        private final RobotAction action;

        public RobotRunnable(final Robot robot, final RobotAction action) {
            this.robot = robot;
            this.action = action;
        }

        @Override
        public void run() {
            try {
                while (this.robot.isStarted()) {
                    action.execute(this.robot.getRobotManager());
                }
            } catch (final Exception exception) {
                LOGGER.error(exception.getMessage(), exception);
                this.robot.stop();
            }
        }
    }
}
