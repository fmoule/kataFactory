package com.b4finance.back.endPoints;

import com.b4finance.back.robot.Robot;
import com.b4finance.back.robot.RobotManager;
import com.b4finance.back.robot.actions.RobotAction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping(path = "/robotManager")
public class RobotManagerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RobotManagerController.class);
    private RobotManager robotManager;
    private List<RobotAction> defaultRobotActions;


    ///// Endpoints :

    @CrossOrigin(origins = "*")
    @PutMapping(path = "/start")
    public ResponseEntity<String> start() {
        if (this.robotManager == null) {
            return status(BAD_REQUEST).body("Erreur: Pas de robotManager !!");
        }
        if (this.robotManager.getRobots().size() == 0) {
            LOGGER.info("---> Creating 2 robots");
            this.robotManager.createNewRobot(this.defaultRobotActions);
            this.robotManager.createNewRobot(this.defaultRobotActions);
        }
        LOGGER.info("===> Starting the robot manager");
        this.robotManager.start();
        return ok("robotManager démarré");
    }

    @CrossOrigin(origins = "*")
    @PutMapping(path = "/stop")
    public ResponseEntity<String> stop() {
        if (this.robotManager == null || !this.robotManager.isStarted()) {
            return ok("robotManager arrêté !!");
        }
        this.robotManager.stop();
        return ok("robotManager arrêté");
    }

    @GetMapping(path = "/get/state")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> fetchState() {
        if (this.robotManager == null) {
            return status(BAD_REQUEST).body("Erreur: pas de robotManager !!");
        } else if (!this.robotManager.isStarted()) {
            return status(BAD_REQUEST).body("Erreur: le robotManager n'est pas démarré !!");
        }
        final List<Robot> robots = this.robotManager.getRobots();
        final JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("nbRobots", robots.size());
        jsonResponse.put("amount", robotManager.getTotalAmount());
        jsonResponse.put("nbFoos", robotManager.getNbFoos());
        jsonResponse.put("nbBars", robotManager.getNbBars());
        jsonResponse.put("nbFoobars", robotManager.getNbFoobars());
        final Map<String, Integer> actionNameMap = new HashMap<>();
        String actionName;
        for (Robot robot : robots) {
            actionName = robot.getCurrentActionName();
            actionNameMap.put(actionName, actionNameMap.getOrDefault(actionName, 0) + 1);
        }
        final JSONArray jsonState = new JSONArray();
        JSONObject jsonStateLine;
        for (String actName : actionNameMap.keySet()) {
            jsonStateLine = new JSONObject();
            jsonStateLine.put(actName, actionNameMap.getOrDefault(actName, 0));
            jsonState.put(jsonStateLine);
        }
        jsonResponse.put("state", jsonState);
        LOGGER.info("Réponse /get/state -> {}", jsonResponse);
        return ok(jsonResponse.toString());
    }

    ///// Setters :

    @Autowired
    public void setRobotManager(@Qualifier("robotManager") final RobotManager robotManager) {
        this.robotManager = robotManager;
    }

    @Autowired
    public void setdefaultActionList(@Qualifier("defaultActionList") final List<RobotAction> defaultRobotActions) {
        this.defaultRobotActions = defaultRobotActions;
    }
}
