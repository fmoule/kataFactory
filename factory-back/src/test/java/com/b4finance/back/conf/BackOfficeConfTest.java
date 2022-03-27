package com.b4finance.back.conf;

import com.b4finance.back.robot.RobotManager;
import com.b4finance.factory.bean.BarBean;
import com.b4finance.factory.bean.FooBarBean;
import com.b4finance.factory.bean.FooBean;
import com.b4finance.factory.bean.Warehouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class BackOfficeConfTest {

    @Autowired
    private BackOfficeConf backOfficeConf;

    private Warehouse<FooBean> fooWarehouse;
    private Warehouse<BarBean> barWarehouse;
    private Warehouse<FooBarBean> foobarBeanWarehouse;
    private RobotManager robotManager;

    //// Tests unitaires :

    @Test
    public void shouldTestConf() {
        assertThat(backOfficeConf).isNotNull();
        assertThat(backOfficeConf.getNbThreads()).isEqualTo(35);
        assertThat(backOfficeConf.getMaxRobots()).isEqualTo(10);
        assertThat(backOfficeConf.getUnitMillis()).isEqualTo(500);
    }

    @Test
    public void shouldGetWarehouses() {
        assertThat(foobarBeanWarehouse).isNotNull();
        assertThat(fooWarehouse).isNotNull();
        assertThat(barWarehouse).isNotNull();
    }

    @Test
    public void shouldGetRobotManager() {
        assertThat(robotManager).isNotNull();
        assertThat(robotManager.getFooWarehouse()).isEqualTo(this.fooWarehouse);
        assertThat(robotManager.getBarWarehouse()).isEqualTo(this.barWarehouse);
        assertThat(robotManager.getFooBarWarehouse()).isEqualTo(this.foobarBeanWarehouse);
        assertThat(robotManager.getExecutorService()).isNotNull();
        assertThat(robotManager.getDefaultRobotActions()).isNotEmpty();
    }

    ///// Getters & Setters :

    @Autowired
    public void setFooWarehouse(@Qualifier("fooWarehouse") Warehouse<FooBean> fooWarehouse) {
        this.fooWarehouse = fooWarehouse;
    }

    @Autowired
    public void setBarWarehouse(@Qualifier("barWarehouse") Warehouse<BarBean> barWarehouse) {
        this.barWarehouse = barWarehouse;
    }

    @Autowired
    public void setFoobarBeanWarehouse(@Qualifier("foobarWarehouse") Warehouse<FooBarBean> foobarBeanWarehouse) {
        this.foobarBeanWarehouse = foobarBeanWarehouse;
    }

    @Autowired
    public void setRobotManager(final RobotManager robotManager) {
        this.robotManager = robotManager;
    }
}