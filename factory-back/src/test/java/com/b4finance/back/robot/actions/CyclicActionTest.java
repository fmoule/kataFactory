package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class CyclicActionTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CyclicActionTest.class);
    private final CyclicAction cyclicAction = new CyclicAction();
    private final RobotManager robotManager = new RobotManager();

    @BeforeEach
    void setUp() {
        this.cyclicAction.clear();
    }

    @Test
    public void shouldExecute() throws Exception {
        this.cyclicAction.addAction(new TestRobotAction(0));
        this.cyclicAction.addAction(new TestRobotAction(1));
        this.cyclicAction.addAction(new TestRobotAction(2));
        this.cyclicAction.execute(robotManager);
        assertThat(this.cyclicAction.getCurrentCursor()).isEqualTo(1);
        assertThat(this.cyclicAction.getName()).isEqualTo("testAction1");
        this.cyclicAction.execute(robotManager);
        assertThat(this.cyclicAction.getCurrentCursor()).isEqualTo(2);
        assertThat(this.cyclicAction.getName()).isEqualTo("testAction2");
        this.cyclicAction.execute(robotManager);
        assertThat(this.cyclicAction.getCurrentCursor()).isEqualTo(0);
        assertThat(this.cyclicAction.getName()).isEqualTo("testAction0");
    }


    private static class TestRobotAction implements RobotAction {
        private final int index;

        public TestRobotAction(final int index) {
            this.index = index;
        }

        @Override
        public void execute(final RobotManager robotManager) {
            LOGGER.info("Execute test action" + index);
        }

        @Override
        public String getName() {
            return "testAction" + index;
        }
    }

}