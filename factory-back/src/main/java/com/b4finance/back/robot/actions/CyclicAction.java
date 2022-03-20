package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CyclicAction implements RobotAction {
    private final List<RobotAction> actions;
    private int cursor;
    private final transient ReentrantReadWriteLock stateLock;

    public CyclicAction() {
        this.actions = new ArrayList<>();
        this.stateLock = new ReentrantReadWriteLock();
    }

    public CyclicAction(final List<RobotAction> robotActions) {
        this.actions = robotActions;
        this.stateLock = new ReentrantReadWriteLock();
    }


    public void addAction(final RobotAction robotAction) {
        this.actions.add(robotAction);
    }

    public void clear() {
        this.actions.clear();
    }

    @Override
    public void execute(final RobotManager robotManager) throws Exception {
        if (actions.isEmpty()) {
            return;
        }
        getCurrentAction().execute(robotManager);
        incrementCursor();
    }

    private void incrementCursor() {
        stateLock.writeLock().lock();
        try {
            cursor = (cursor + 1) % (this.actions.size());
        } finally {
            stateLock.writeLock().unlock();
        }
    }


    ///// Getters :

    private RobotAction getCurrentAction() {
        return this.actions.get(this.getCurrentCursor());
    }

    @Override
    public String getName() {
        final RobotAction currentAction = this.getCurrentAction();
        return (currentAction == null ? "" : currentAction.getName());
    }

    public int getCurrentCursor() {
        stateLock.readLock().lock();
        try {
            return this.cursor;
        } finally {
            stateLock.readLock().unlock();
        }
    }


    public void setActions(final List<RobotAction> robotActions) {
        this.actions.clear();
        this.actions.addAll(robotActions);
    }
}
