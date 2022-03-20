package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

import static java.time.temporal.ChronoUnit.SECONDS;

public abstract class AbstractSimpleRobotAction implements RobotAction {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractSimpleRobotAction.class);
    private final String name;
    protected Duration unitDuration;

    public AbstractSimpleRobotAction(final String name) {
        this(name, 1, SECONDS);
    }

    public AbstractSimpleRobotAction(final String name, final long amount, final TemporalUnit unit) {
        this.unitDuration = Duration.of(amount, unit);
        this.name = name;
    }

    @Override
    public void execute(final RobotManager robotManager) throws Exception {
        try {
            this.doExecute(robotManager);
        } catch (final InterruptedException interruptedException) {
            LOGGER.info("---> " + this + " a été interrompue");
        }

    }

    protected abstract void doExecute(RobotManager robotManager) throws Exception;

    @Override
    public String toString() {
        return "Action(name = '" + this.name + "')";
    }

    ///// Getters :

    @Override
    public String getName() {
        return this.name;
    }

    public long getUnitDurationMillis() {
        return unitDuration.toMillis();
    }

    public void setUnitDuration(final long amount, final TemporalUnit unit) {
        this.unitDuration = Duration.of(amount, unit);
    }
}
