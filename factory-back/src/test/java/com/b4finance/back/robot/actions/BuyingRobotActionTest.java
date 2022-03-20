package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;
import com.b4finance.factory.bean.FooBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class BuyingRobotActionTest {
    private RobotManager robotManager;
    private BuyingRobotAction action;

    private void initRobotManager(final long totalAmount, final int nbFoos) {
        this.robotManager.getWallet().setTotalAmount(((Number) totalAmount).longValue());
        for (int i = 0; i < nbFoos; i++) {
            this.robotManager.putFooBean(new FooBean());
        }
    }

    ///// Initialisation :

    @BeforeEach
    void setUp() {
        this.action = new BuyingRobotAction(new ArrayList<>());
        this.robotManager = new RobotManager();
    }

    ///// Tests unitaires :

    @Test
    public void shouldExecute() throws Exception {
        this.initRobotManager(7, 7);
        assertThat(this.robotManager.getRobots()).isEmpty();
        this.action.execute(this.robotManager);
        assertThat(this.robotManager.getRobots()).isNotEmpty();
        assertThat(this.robotManager.getRobots().size()).isEqualTo(1);

        // On vérifie le nbre
        assertThat(this.robotManager.getTotalAmount()).isEqualTo(4L);
        assertThat(this.robotManager.getNbFoos()).isEqualTo(1L);
    }

    @Test
    public void shouldNotExecute() throws Exception {
        this.initRobotManager(2, 5);
        assertThat(this.robotManager.getRobots()).isEmpty();
        this.action.execute(this.robotManager);
        assertThat(this.robotManager.getRobots()).isEmpty();

        // On vérifie le nbre
        assertThat(this.robotManager.getTotalAmount()).isEqualTo(2L);
        assertThat(this.robotManager.getNbFoos()).isEqualTo(5L);
    }

    @Test
    public void shouldNotExecute_case2() throws Exception {
        this.initRobotManager(4, 5);
        assertThat(this.robotManager.getRobots()).isEmpty();
        this.action.execute(this.robotManager);
        assertThat(this.robotManager.getRobots()).isEmpty();

        // On vérifie le nbre
        assertThat(this.robotManager.getTotalAmount()).isEqualTo(4);
        assertThat(this.robotManager.getNbFoos()).isEqualTo(5);
    }
}