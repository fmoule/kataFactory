package com.b4finance.factory.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class WarehouseTest {
    private final Warehouse<TestBean> warehouse = new Warehouse<>();

    @BeforeEach
    void setUp() {
        this.warehouse.clear();
    }

    ///// Tests unitaires :

    @Test
    public void shouldAdd() {
        assertThat(warehouse.size()).isEqualTo(0);
        warehouse.put(new TestBean("testBean1"));
        warehouse.put(new TestBean("testBean2"));
        assertThat(warehouse.size()).isEqualTo(2);
    }

    @Test
    public void shouldFetch() {
        warehouse.put(new TestBean("testBean1"));
        warehouse.put(new TestBean("testBean2"));
        final TestBean testBean = warehouse.fetchbean();
        assertThat(testBean).isNotNull();
        assertThat(warehouse.size()).isEqualTo(1);
    }


    @Test
    public void shouldFetchBeans() {
        for (int i = 0; i < 10; i++) {
            warehouse.put(new TestBean("testBean" + i));
        }
        final List<TestBean> retrievedBeans = warehouse.fetchBeans(5);
        assertThat(retrievedBeans).isNotEmpty();
        assertThat(retrievedBeans.size()).isEqualTo(5);
        assertThat(warehouse.size()).isEqualTo(5);
    }

    @Test
    public void shouldFetchBeans_case2() {
        for (int i = 0; i < 10; i++) {
            warehouse.put(new TestBean("testBean" + i));
        }
        final List<TestBean> retrievedBeans = warehouse.fetchBeans(7);
        assertThat(retrievedBeans).isNotEmpty();
        assertThat(retrievedBeans.size()).isEqualTo(7);
        assertThat(warehouse.size()).isEqualTo(3);
    }

    ///// Classes internes :

    private static class TestBean {
        private final String beanName;

        private TestBean(final String beanName) {
            this.beanName = beanName;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.beanName);
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if ((obj == null) || (obj.getClass() != this.getClass())) {
                return false;
            }
            return Objects.equals(this.beanName, ((TestBean) obj).beanName);
        }

        public String getBeanName() {
            return beanName;
        }
    }
}