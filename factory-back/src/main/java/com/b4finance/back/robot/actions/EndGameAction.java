package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;

public class EndGameAction extends AbstractSimpleRobotAction {
    private final int maxRobots;

    public EndGameAction(final int maxRobots) {
        super("endGameAction");
        this.maxRobots = maxRobots;
    }

    @Override
    protected void doExecute(final RobotManager robotManager) {
        if (robotManager != null
                && robotManager.getRobots().size() >= this.maxRobots) {
            LOGGER.info("=====================");
            LOGGER.info("===> Le jeu est fini");
            LOGGER.info("=====================");
            robotManager.stop();
        }
    }
}
