package com.b4finance.back.robot.actions;

import com.b4finance.back.robot.RobotManager;

/**
 * <p>
 * Interface représentant les actions des robets. <br />
 * </p>
 */
public interface RobotAction {

    /**
     * Execute l'action
     *
     * @param robotManager
     * @throws Exception: en cas de problème
     */
    void execute(RobotManager robotManager) throws Exception;

    /**
     * Retourne le nom de l'action. <br />
     *
     * @return nom de l'action
     */
    String getName();
}
