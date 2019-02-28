package com.mkyong.config;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * https://dbdiagram.io/d/5c6ef578f7c5bb70c72f15f6
 *
 * @see https://devcolibri.com/spring-data-jpa-работа-с-бд-часть-1
 *      http://qaru.site/questions/994811/springboot-no-qualifying-bean-of-type-javaxsqldatasource
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.mkyong.repository")
public class DataConfig {

    private final String ENTITYMANAGER_PACKAGES_TO_SCAN = "com.mkyong.entity";

    @Value("${spring.datasource.url:}")
    private String PROP_DATABASE_URL;

    @Value("${spring.datasource.username:}")
    private String PROP_DATABASE_USERNAME;

    @Value("${spring.datasource.password:}")
    private String PROP_DATABASE_PASSWORD;

    @Value("${spring.datasource.driver-class-name:}")
    private String PROP_DATABASE_DRIVER;

    @Value("${spring.jpa.show-sql:}")
    private String PROP_HIBERNATE_SHOW_SQL;

    @Value("${spring.jpa.hibernate.ddl-auto:}")
    private String PROP_HIBERNATE_HBM2DDL_AUTO;

    @Value("${spring.jpa.properties.hibernate.dialect:}")
    private String PROP_HIBERNATE_DIALECT;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
        return entityManagerFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(PROP_DATABASE_DRIVER);
        dataSource.setUrl(PROP_DATABASE_URL);
        dataSource.setUsername(PROP_DATABASE_USERNAME);
        dataSource.setPassword(PROP_DATABASE_PASSWORD);
        return dataSource;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(PROP_HIBERNATE_DIALECT, PROP_HIBERNATE_DIALECT);
        properties.put(PROP_HIBERNATE_SHOW_SQL, PROP_HIBERNATE_SHOW_SQL);
        properties.put(PROP_HIBERNATE_HBM2DDL_AUTO, PROP_HIBERNATE_HBM2DDL_AUTO);
        return properties;
    }
}