package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;

class EndGameActionTest {
    private RobotManager robotManager;
    private RobotAction action;

    private void initRobotManager(final int nbRobots) {
        for (int i = 0; i < nbRobots; i++) {
            this.robotManager.createNewRobot(new ArrayList<>());
        }
    }

    @BeforeEach
    void setUp() {
        this.robotManager = new RobotManager();
        this.robotManager.setExecutorService(new ThreadPoolExecutor(2, 5, 250, MILLISECONDS, new LinkedBlockingQueue<>()));
        this.action = new EndGameAction(5);
    }

    ///// Tests unitaires :

    @Test
    public void shouldExecute() throws Exception {
        this.initRobotManager(5);
        this.robotManager.start();
        assertThat(robotManager.isStarted()).isTrue();
        this.action.execute(this.robotManager);
        Thread.sleep(250);
        assertThat(robotManager.isStarted()).isFalse();
    }


    @Test
    public void shouldNotEndTheGame() throws Exception {
        try {
            this.initRobotManager(3);
            this.robotManager.start();
            assertThat(robotManager.isStarted()).isTrue();
            this.action.execute(this.robotManager);
            Thread.sleep(250);
            assertThat(robotManager.isStarted()).isTrue();
        } finally {
            this.robotManager.stop();
        }
    }

}