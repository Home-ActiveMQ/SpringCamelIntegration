package com.mkyong.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EntityScan("com.mkyong")
public class DatabaseConfig {

    /**
     * @see https://coderoad.ru/23214454/org-hibernate-MappingException-неизвестная-сущность-annotations-Users
     *      https://overcoder.net/q/1288149/транзакция-не-была-успешно-запущена-в-то-время-как-txcommit-окружен-условием-if
     */
//    @Bean(name = "h2SessionFactory")
//    public SessionFactory h2SessionFactory() throws PropertyVetoException {
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .configure("hibernate.cfg.xml")
//                .build();
//        return new org.hibernate.cfg.Configuration().buildSessionFactory(serviceRegistry);
//    }

    @Bean(name = "h2SessionFactory")
    public LocalSessionFactoryBean h2SessionFactory() throws PropertyVetoException {
        LocalSessionFactoryBean h2SessionFactory = new LocalSessionFactoryBean();
        h2SessionFactory.setDataSource(h2DataSource());
        h2SessionFactory.setPackagesToScan("com.mkyong");
        h2SessionFactory.setHibernateProperties(hibernateH2Properties());

        return h2SessionFactory;
    }

    private DataSource h2DataSource() throws PropertyVetoException {
        HikariDataSource h2dataSource = new HikariDataSource();
        h2dataSource.setDriverClassName("org.h2.Driver");
        h2dataSource.setJdbcUrl("jdbc:h2:mem:FASTTACK_H2;DB_CLOSE_DELAY=-1"); // h2dataSource.setJdbcUrl("jdbc:h2:mem:test");
        h2dataSource.setUsername("sa");
        h2dataSource.setPassword("1");
        return h2dataSource;
    }

    private Properties hibernateH2Properties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        return properties;
    }
}
