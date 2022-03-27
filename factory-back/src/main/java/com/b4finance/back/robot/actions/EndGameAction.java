package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;

public class EndGameAction extends AbstractRobotAction {
    private final int maxRobots;

    public EndGameAction(final int maxRobots) {
        super("endGameAction");
        this.maxRobots = maxRobots;
    }

    @Override
    protected void doExecute(final RobotManager robotManager) {
        int nbRobots = (robotManager == null ? 0 : robotManager.getRobots().size());
        if (robotManager == null || nbRobots < this.maxRobots) {
            LOGGER.info("---> We continue because nbRobots = {} < {}", nbRobots, this.maxRobots);
            return;
        }
        LOGGER.info("=====================");
        LOGGER.info("===> Le jeu est fini");
        LOGGER.info("=====================");
        robotManager.stop();
    }
}
