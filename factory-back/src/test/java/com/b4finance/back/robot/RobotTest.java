package com.b4finance.back.robot;

import com.b4finance.back.robot.actions.RobotAction;
import com.b4finance.factory.bean.FooBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

import static java.lang.Thread.sleep;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class RobotTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RobotTest.class);
    private RobotManager robotManager;

    ///// Initialisation

    @BeforeEach
    void setUp() {
        this.robotManager = new RobotManager();
        this.robotManager.setNbThreads(10);
    }

    ///// Tests unitaires :

    @Test
    public void shouldBeEquals() {
        final Robot robot = new Robot("robot", this.robotManager);
        assertThat(robot).isEqualTo(new Robot("robot", this.robotManager));
        assertThat(robot.hashCode()).isEqualTo(new Robot("robot", this.robotManager).hashCode());
        assertThat(robot).isNotEqualTo(new Robot("otherRobot", this.robotManager));
        assertThat(robot).isNotEqualTo(null);
        assertThat(robot).isEqualTo(robot);
    }

    @Test
    public void shouldExecute() throws Exception {
        final Robot robot = new Robot("robot", robotManager);
        robot.setUnitDuration(20, MILLIS);
        robot.setActions(asList(new TestAction("action1", 3, SECONDS), new TestAction("action2", 5, SECONDS)));
        robot.start();
        assertThat(robot.getCurrentActionName()).isEqualTo("action1");
        sleep(3125);
        assertThat(robot.getCurrentActionName()).isEqualTo("action2");
        assertThat(robotManager.getNbFoos()).isEqualTo(1);
        sleep(5125);
        assertThat(robotManager.getNbFoos()).isEqualTo(2);
        robot.stop();
        sleep(1000);
        assertThat(robot.isStarted()).isFalse();
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
        public void execute(final RobotManager robotManager) throws Exception {
            sleep(waitingDuration.toMillis());
            LOGGER.info(this.name + ": buy Foo");
            robotManager.putFooBean(new FooBean());
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

}