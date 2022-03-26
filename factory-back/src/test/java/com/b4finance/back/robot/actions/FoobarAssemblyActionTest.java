package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;
import com.b4finance.factory.bean.BarBean;
import com.b4finance.factory.bean.FooBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FoobarAssemblyActionTest {
    private final FoobarAssemblyAction foobarAssemblyAction = new FoobarAssemblyAction();
    private RobotManager robotManager;

    @BeforeEach
    void setUp() {
        this.robotManager = new RobotManager();
    }

    private void initWarehouses(final int nbFoos, final int nbBars) {
        for (int i = 0; i < nbFoos; i++) {
            this.robotManager.putFooBean(new FooBean());
        }
        for (int i = 0; i < nbBars; i++) {
            this.robotManager.putBarBean(new BarBean());
        }
    }

    ///// Tests unitaires :

    @Test
    public void shouldExecuteWithSuccess() throws Exception {
        this.initWarehouses(7, 3);
        foobarAssemblyAction.doExecute(robotManager, 0.5);
        assertThat(robotManager.getNbFoobars()).isEqualTo(1);
        assertThat(robotManager.getNbFoos()).isEqualTo(6);
        assertThat(robotManager.getNbBars()).isEqualTo(2);
    }

    @Test
    public void shouldExecuteWithFail() throws Exception {
        this.initWarehouses(7, 3);
        foobarAssemblyAction.doExecute(robotManager, 0.654);
        assertThat(robotManager.getNbFoobars()).isEqualTo(0);
        assertThat(robotManager.getNbFoos()).isEqualTo(6);
        assertThat(robotManager.getNbBars()).isEqualTo(3);
    }

}