package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;
import com.b4finance.factory.bean.BarBean;
import com.b4finance.factory.bean.FooBarBean;
import com.b4finance.factory.bean.FooBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static java.time.Duration.between;
import static java.time.temporal.ChronoUnit.MILLIS;
import static org.assertj.core.api.Assertions.assertThat;

class SellingFoobarActionTest {
    private SellingFoobarAction action;
    private RobotManager robotManager;

    private void initRobotManager(final int nbFooBars) {
        if (this.robotManager == null) {
            return;
        }
        for (int i = 0; i < nbFooBars; i++) {
            this.robotManager.putFoobarBean(new FooBarBean(new FooBean(), new BarBean()));
        }
    }

    ///// Initialisation :

    @BeforeEach
    void setUp() {
        this.action = new SellingFoobarAction();
        this.action.setUnitDuration(100, MILLIS);
        robotManager = new RobotManager();
    }

    ///// Tests unitaires :


    @Test
    public void shouldExecute() throws Exception {
        this.initRobotManager(7);
        final ZonedDateTime beginDateTime = ZonedDateTime.now();
        action.execute(this.robotManager);
        final ZonedDateTime endDateTime = ZonedDateTime.now();
        assertThat(between(beginDateTime, endDateTime).getSeconds() >= 2).isTrue();
        assertThat(robotManager.getNbFoobars()).isEqualTo(0);
        assertThat(robotManager.getTotalAmount()).isEqualTo(7);
    }

    @Test
    public void shouldExecute_case2() throws Exception {
        this.initRobotManager(3);
        final ZonedDateTime beginDateTime = ZonedDateTime.now();
        action.execute(this.robotManager);
        final ZonedDateTime endDateTime = ZonedDateTime.now();
        assertThat(between(beginDateTime, endDateTime).getSeconds() >= 1).isTrue();
        assertThat(robotManager.getNbFoobars()).isEqualTo(0);
        assertThat(robotManager.getTotalAmount()).isEqualTo(3);
    }
}