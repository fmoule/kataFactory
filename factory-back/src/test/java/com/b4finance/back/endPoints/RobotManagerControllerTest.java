package com.b4finance.back.endPoints;

import com.b4finance.back.robot.RobotManager;
import com.b4finance.back.robot.actions.AbstractSimpleRobotAction;
import com.b4finance.factory.bean.BarBean;
import com.b4finance.factory.bean.FooBarBean;
import com.b4finance.factory.bean.FooBean;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Thread.sleep;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

class RobotManagerControllerTest {
    private RobotManager robotManager;
    private RobotManagerController robotManagerController;

    private void initRobotManager(final int nbFoos,
                                  final int nbBars,
                                  final int nbFoobars,
                                  final long amount) {
        for (int i = 0; i < nbFoos; i++) {
            robotManager.putFooBean(new FooBean());
        }
        for (int i = 0; i < nbBars; i++) {
            robotManager.putBarBean(new BarBean());
        }
        for (int i = 0; i < nbFoobars; i++) {
            robotManager.putFoobarBean(new FooBarBean(new FooBean(), new BarBean()));
        }
        this.robotManager.getWallet().addAmount(amount);
    }

    ///// Initialisation :

    @BeforeEach
    void setUp() {
        this.robotManager = new RobotManager();
        this.robotManager.setExecutorService(new ThreadPoolExecutor(2, 5, 250, MILLISECONDS, new LinkedBlockingQueue<>()));
        this.robotManagerController = new RobotManagerController();
        this.robotManagerController.setRobotManager(robotManager);
    }

    @AfterEach
    void tearDown() {
        if (this.robotManager != null && this.robotManager.isStarted()) {
            this.robotManager.stop();
        }
    }

    ///// Tests unitaires :

    @Test
    public void shouldGetResponse() throws Exception {
        this.initRobotManager(5, 7, 10, 120);
        this.robotManager.createNewRobot(singletonList(new TestRobotAction("action1")));
        this.robotManager.createNewRobot(singletonList(new TestRobotAction("action2")));
        this.robotManager.start();
        try {
            final ResponseEntity<String> httpResponse = this.robotManagerController.fetchState();
            assertThat(httpResponse).isNotNull();
            assertThat(httpResponse.getStatusCode()).isEqualTo(OK);
            sleep(300);
            final JSONObject jsonResponse = new JSONObject(httpResponse.getBody());
            assertThat(jsonResponse.getInt("nbRobots")).isEqualTo(2);
            assertThat(jsonResponse.getLong("amount")).isEqualTo(120L);
            assertThat(jsonResponse.getInt("nbFoos")).isEqualTo(5);
            assertThat(jsonResponse.getInt("nbBars")).isEqualTo(7);
            assertThat(jsonResponse.getInt("nbFoobars")).isEqualTo(10);

            // Vérification de l'état :
            final JSONArray state = jsonResponse.getJSONArray("state");
            assertThat(state).isNotNull();
            assertThat(state.length()).isEqualTo(2);
        } finally {
            this.robotManager.stop();
        }
    }

    @Test
    public void shouldThrowErrorIfRobotManagerNotStarted() {
        this.initRobotManager(4, 8, 9, 45);
        this.robotManager.createNewRobot(singletonList(new TestRobotAction("action1")));
        this.robotManager.createNewRobot(singletonList(new TestRobotAction("action2")));

        // On vérifie la réponse :
        final ResponseEntity<String> httpResponse = this.robotManagerController.fetchState();
        assertThat(httpResponse).isNotNull();
        assertThat(httpResponse.getStatusCode()).isEqualTo(BAD_REQUEST);
        assertThat(httpResponse.getBody()).isEqualTo("Erreur: le robotManager n'est pas démarré !!");
    }

    @Test
    public void shouldThrowErrorIfRobotManagerIsNull() {

        // Pas de robot manager !!
        this.robotManagerController.setRobotManager(null);

        // On vérifie la réponse :
        final ResponseEntity<String> httpResponse = this.robotManagerController.fetchState();
        assertThat(httpResponse).isNotNull();
        assertThat(httpResponse.getStatusCode()).isEqualTo(BAD_REQUEST);
        assertThat(httpResponse.getBody()).isEqualTo("Erreur: pas de robotManager !!");
    }


    @Test
    public void shouldStop() throws Exception {
        this.initRobotManager(5, 7, 10, 120);
        this.robotManager.createNewRobot(singletonList(new TestRobotAction("action1")));
        this.robotManager.createNewRobot(singletonList(new TestRobotAction("action2")));
        final ResponseEntity<String> httpResponse = this.robotManagerController.start();
        assertThat(httpResponse).isNotNull();
        assertThat(httpResponse.getStatusCode()).isEqualTo(OK);
        assertThat(httpResponse.getBody()).isEqualTo("robotManager démarré");
        sleep(250);
        assertThat(this.robotManager.isStarted()).isTrue();
    }

    ///// Classe(s) interne(s) :

    private static class TestRobotAction extends AbstractSimpleRobotAction {

        private TestRobotAction(final String name) {
            super(name);
        }

        @Override
        protected void doExecute(final RobotManager robotManager) throws Exception {
            LOGGER.info("--> Execution de {}", this.getName());
            sleep(750L);
        }
    }
}