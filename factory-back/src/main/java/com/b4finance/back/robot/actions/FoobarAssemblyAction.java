package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;
import com.b4finance.factory.bean.BarBean;
import com.b4finance.factory.bean.FooBarBean;
import com.b4finance.factory.bean.FooBean;

import java.math.BigDecimal;

import static com.b4finance.factory.utils.RandomUtils.random;
import static java.lang.Thread.sleep;

public class FoobarAssemblyAction extends AbstractSimpleRobotAction {
    private static final BigDecimal SUCCESS_PERCENTAGE = new BigDecimal("0.6");

    public FoobarAssemblyAction() {
        super("assemblyFooBar");
    }

    @Override
    public void doExecute(final RobotManager robotManager) throws Exception {
        doExecute(robotManager, random());
    }

    void doExecute(final RobotManager robotManager, final Number randomNumber) throws Exception {
        if (robotManager.getNbFoos() < 6) {
            LOGGER.info("---> we keep 6 foos to buy robots so we don't assembly a new foobar !!");
            return;
        }
        final FooBean fooBean = robotManager.fetchFooBean();
        final BarBean barBean = robotManager.fetchBarBean();
        if (fooBean == null || barBean == null) {
            return;
        }
        LOGGER.info("---> Assemblying foobar");
        sleep(2 * this.getUnitDurationMillis());
        if (new BigDecimal(randomNumber.toString()).compareTo(SUCCESS_PERCENTAGE) > 0) {
            robotManager.putBarBean(barBean);
            return;
        }
        robotManager.putFoobarBean(new FooBarBean(fooBean, barBean));
    }

}
