package com.b4finance.back.conf;

import com.b4finance.back.robot.RobotManager;
import com.b4finance.back.robot.actions.*;
import com.b4finance.factory.bean.BarBean;
import com.b4finance.factory.bean.FooBarBean;
import com.b4finance.factory.bean.FooBean;
import com.b4finance.factory.bean.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BackOfficeConf {
    private int nbThreads;
    private int maxRobots;
    private long unitMillis;

    @Bean(name = "fooWarehouse")
    public Warehouse<FooBean> buildFooBeanWarehouse() {
        return new Warehouse<>();
    }

    @Bean(name = "barWarehouse")
    public Warehouse<BarBean> buildBarBeanWarehouse() {
        return new Warehouse<>();
    }

    @Bean(name = "foobarWarehouse")
    public Warehouse<FooBarBean> buildFooBarBeanWarehouse() {
        return new Warehouse<>();
    }

    @Bean(name = "robotManager")
    public RobotManager buildRobotManager(@Qualifier("fooWarehouse") final Warehouse<FooBean> fooWarehouse,
                                          @Qualifier("barWarehouse") final Warehouse<BarBean> barWarehouse,
                                          @Qualifier("foobarWarehouse") final Warehouse<FooBarBean> foobarWarehouse) {
        final RobotManager robotManager = new RobotManager(fooWarehouse, barWarehouse, foobarWarehouse);
        robotManager.setNbThreads(nbThreads);
        robotManager.setUnitMillis(this.unitMillis);
        robotManager.setDefaultRobotActions(buildDefaultRobotActions());
        return robotManager;
    }

    @Bean(name = "defaultActionList")
    public List<RobotAction> buildDefaultRobotActions() {
        final List<RobotAction> robotActions = new ArrayList<>();
        robotActions.add(new FooMiningAction());
        robotActions.add(new BarMiningAction());
        robotActions.add(new FoobarAssemblyAction());
        robotActions.add(new SellingFoobarAction());
        robotActions.add(new BuyingRobotAction(robotActions));
        robotActions.add(new EndGameAction(this.maxRobots));
        return robotActions;
    }

    ///// Getters & Setters :

    @Autowired
    public void setNbThreads(@Value("${back.nb.threads}") final int nbThreads) {
        this.nbThreads = nbThreads;
    }

    public int getNbThreads() {
        return this.nbThreads;
    }

    @Autowired
    public void setMaxRobots(@Value("${back.max.robots}") final int maxRobots) {
        this.maxRobots = maxRobots;
    }

    @Bean(name = "maxRobots")
    public Integer getMaxRobots() {
        return maxRobots;
    }

    public long getUnitMillis() {
        return unitMillis;
    }

    @Autowired
    public void setUnitMillis(@Value("${back.unit.millis}") final Long unitMillis) {
        this.unitMillis = (unitMillis == null ? 1000 : unitMillis);
    }
}
