package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;
import com.b4finance.factory.bean.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FooMiningActionTest {
    private RobotManager robotManager;

    @BeforeEach
    void setUp() {
        robotManager = new RobotManager();
    }

    ///// Tests unitaires :

    @Test
    public void shouldExecute() throws Exception {
        final FooMiningAction fooMiningAction = new FooMiningAction();
        fooMiningAction.execute(this.robotManager);
        assertThat(robotManager.getFooWarehouse().size()).isEqualTo(1);
    }

}