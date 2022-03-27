package com.b4finance.back.robot;

import com.b4finance.back.robot.actions.RobotAction;
import com.b4finance.factory.bean.FooBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Thread.sleep;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;

class RobotManagerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RobotManagerTest.class);
    private RobotManager robotManager;

    @BeforeEach
    void setUp() {
        this.robotManager = new RobotManager();
        this.robotManager.setNbThreads(10);
    }

    ///// Tests unitaires :

    @Test
    public void shouldStartOneRobotBeforeStarting() throws Exception {
        this.robotManager.createNewRobot(asList(new TestAction("action1", 2, SECONDS), new TestAction("action2", 3, SECONDS)));
        final List<Robot> robots = this.robotManager.getRobots();
        assertThat(robots).isNotEmpty();
        assertThat(robots.size()).isEqualTo(1);
        this.robotManager.start();
        assertThat(robots.get(0).isStarted()).isTrue();
        sleep(2100);
        assertThat(this.robotManager.getNbFoos()).isEqualTo(1);
        sleep(3100);
        assertThat(this.robotManager.getNbFoos()).isEqualTo(2);
        this.robotManager.stop();
        sleep(500);
        assertThat(this.robotManager.isStarted()).isFalse();
    }

    @Test
    public void shouldStartOneRobotAfterStarting() throws Exception {
        this.robotManager.createNewRobot(asList(new TestAction("action1", 2, SECONDS), new TestAction("action2", 3, SECONDS)));
        this.robotManager.start();
        final List<Robot> robots = this.robotManager.getRobots();
        assertThat(robots).isNotEmpty();
        assertThat(robots.size()).isEqualTo(1);
        assertThat(robots.get(0).isStarted()).isTrue();
        sleep(2100);
        assertThat(this.robotManager.getNbFoos()).isEqualTo(1);
        sleep(3100);
        assertThat(this.robotManager.getNbFoos()).isEqualTo(2);
        this.robotManager.stop();
        sleep(500);
        assertThat(this.robotManager.isStarted()).isFalse();
    }


    ///// Classe(s) interne(s) :

    private static class TestAction implements RobotAction {
        private final Duration waitingDuration;
        private final String name;

        private TestAction(final String name, final long amount, final TemporalUnit tempUnit) {
            this.waitingDuration = Duration.of(amount, tempUnit);
            this.name = name;
        }

        @Override
        public void execute(final RobotManager robotManager) {
            try {
                sleep(waitingDuration.toMillis());
                LOGGER.info(this.name + ": buy Foo");
                robotManager.putFooBean(new FooBean());
            } catch (final InterruptedException interruptedException) {
                LOGGER.info("--> " + this.getName() + " a été interrompue");
            }
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

}