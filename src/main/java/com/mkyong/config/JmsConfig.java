package com.mkyong.config;

import com.mkyong.jms.routes.QueueRoutes;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static com.mkyong.util.JmsUtil.JMS;

@Configuration
public class JmsConfig {

    @Autowired
    private QueueRoutes queueRoutes;

    @Value("${activeMQConnectionFactory.brokerURL:}")
    private String brokerURL;

    @Value("${pooledConnectionFactory.maxConnections:8}")
    private int maxConnections;

    @Value("${pooledConnectionFactory.maximumActiveSessionPerConnection:8}")
    private int maximumActiveSessionPerConnection;

    @Value("${jmsConfiguration.concurrentConsumers:1}")
    private int concurrentConsumers;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        connectionFactory.setUseAsyncSend(true);
        connectionFactory.setSendAcksAsync(true);
        connectionFactory.setAlwaysSessionAsync(true);
        return connectionFactory;
    }

    @Bean
    public PooledConnectionFactory pooledConnectionFactory() {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(connectionFactory());
        pooledConnectionFactory.setMaxConnections(maxConnections);
        pooledConnectionFactory.setMaximumActiveSessionPerConnection(maximumActiveSessionPerConnection);
        return pooledConnectionFactory;
    }

    @Bean
    public JmsConfiguration jmsConfiguration() {
        JmsConfiguration jmsConfiguration = new JmsConfiguration();
        jmsConfiguration.setConnectionFactory(pooledConnectionFactory());
        jmsConfiguration.setConcurrentConsumers(concurrentConsumers);
        jmsConfiguration.setTransacted(false);
        return jmsConfiguration;
    }

    @Bean
    public CamelContext camelContext() throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addComponent(JMS, JmsComponent.jmsComponentAutoAcknowledge(pooledConnectionFactory()));
        camelContext.addRoutes(queueRoutes);
        camelContext.start();
        return camelContext;
    }
}
