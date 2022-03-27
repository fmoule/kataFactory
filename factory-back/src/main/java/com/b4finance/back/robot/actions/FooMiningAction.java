package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;
import com.b4finance.factory.bean.FooBean;

import static java.lang.Thread.sleep;

public class FooMiningAction extends AbstractRobotAction {

    public FooMiningAction() {
        super("miningFoo");
    }

    @Override
    public void doExecute(final RobotManager robotManager) throws Exception {
        LOGGER.info("---> mining foo");
        sleep(this.getUnitDurationMillis());
        robotManager.putFooBean(new FooBean());
    }

}
