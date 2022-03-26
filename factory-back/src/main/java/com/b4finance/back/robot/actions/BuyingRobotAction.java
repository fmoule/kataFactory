package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;

import java.util.List;

public class BuyingRobotAction extends AbstractSimpleRobotAction {
    private final List<RobotAction> defaultActions;

    public BuyingRobotAction(final List<RobotAction> defaultActions) {
        super("buyingRobot");
        this.defaultActions = defaultActions;
    }

    @Override
    public void doExecute(final RobotManager robotManager) {
        if (robotManager.getTotalAmount() < 3 || robotManager.getNbFoos() < 6) {
            return;
        }
        LOGGER.info("---> buying 1 robot");
        robotManager.getWallet().spendAmount(3);
        robotManager.getFooWarehouse().fetchBeans(6);
        robotManager.createNewRobot(this.defaultActions);
    }
}
