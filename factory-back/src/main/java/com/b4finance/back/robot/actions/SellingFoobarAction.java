package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;

import static com.b4finance.factory.utils.NumberUtils.longPart;
import static java.lang.Thread.sleep;

public class SellingFoobarAction extends AbstractSimpleRobotAction {

    public SellingFoobarAction() {
        super("SellingFoobar");
    }

    @Override
    public void doExecute(final RobotManager robotManager) throws Exception {
        final int totalNbFoobars = robotManager.getNbFoobars();
        if (totalNbFoobars == 0) {
            return;
        }
        sleep((longPart(totalNbFoobars / 5) + 1) * (10 * this.getUnitDurationMillis()));
        LOGGER.info("---> Selling {} foobars", totalNbFoobars);
        robotManager.addAmount(totalNbFoobars);
        robotManager.getFooBarWarehouse().fetchBeans(totalNbFoobars);
    }
}
