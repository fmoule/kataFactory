package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

public class CyclicAction extends AbstractRobotAction {
    private final List<RobotAction> actions;
    private int cursor;
    private final transient ReentrantReadWriteLock stateLock;

    public CyclicAction() {
        super("cyclicAction");
        this.actions = new ArrayList<>();
        this.stateLock = new ReentrantReadWriteLock();
    }

    public void addAction(final RobotAction robotAction) {
        this.actions.add(robotAction);
    }

    public void clear() {
        this.actions.clear();
    }

    @Override
    protected void doExecute(final RobotManager robotManager) throws Exception {
        if (actions.isEmpty()) {
            return;
        }
        getCurrentAction().execute(robotManager);
        sleep(5 * this.getUnitDurationMillis());
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
