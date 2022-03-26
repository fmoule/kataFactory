package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;
import com.b4finance.factory.bean.BarBean;

import static com.b4finance.factory.utils.RandomUtils.random;
import static java.lang.Thread.sleep;

public class BarMiningAction extends AbstractSimpleRobotAction {

    public BarMiningAction() {
        super("miningBar");
    }

    @Override
    public void doExecute(final RobotManager robotManager) throws Exception {
        LOGGER.info("---> mining bar");
        sleep(random(0.5, 2).longValue() * this.getUnitDurationMillis());
        robotManager.getBarWarehouse().put(new BarBean());
    }
}
