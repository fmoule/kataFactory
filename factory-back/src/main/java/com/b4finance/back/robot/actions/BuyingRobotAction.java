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
        long totalAmount = robotManager.getTotalAmount();
        int nbFoos = robotManager.getNbFoos();
        if (totalAmount < 3 || nbFoos < 6) {
            LOGGER.info("---> No buying robots because {} < 3 euros and {} < 6 foos", totalAmount, nbFoos);
            return;
        }
        LOGGER.info("---> buying 1 robot");
        robotManager.getWallet().spendAmount(3);
        robotManager.getFooWarehouse().fetchBeans(6);
        robotManager.createNewRobot(this.defaultActions);
    }
}
