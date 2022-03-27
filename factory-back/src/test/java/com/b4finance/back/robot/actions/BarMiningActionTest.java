package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BarMiningActionTest {
    private RobotManager robotManager;

    @BeforeEach
    void setUp() {
        this.robotManager = new RobotManager();
    }

    ///// Tests unitaires :

    @Test
    public void shouldExecute() throws Exception {
        final BarMiningAction barMiningAction = new BarMiningAction();
        barMiningAction.execute(this.robotManager);
        assertThat(robotManager.getBarWarehouse().size()).isEqualTo(1);
    }
}