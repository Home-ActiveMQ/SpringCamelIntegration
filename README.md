
* `spring-boot-starter` https://www.mkyong.com/spring-boot/spring-boot-non-web-application-example
* `Log4j2 xml configuration example` https://howtodoinjava.com/log4j2/log4j-2-xml-configuration-example
* `changing-date-format-in-log4j` http://logging.apache.org/log4j/2.x/manual/layouts.html `https://stackoverflow.com/questions/21979606/changing-date-format-in-log4j-xml`, `https://logging.apache.org/log4j/log4j-2.1/manual/layouts.html`


* `Spring Boot @ConfigurationProperties example` https://www.mkyong.com/spring-boot/spring-boot-configurationproperties-example

Spring Boot with Camel ActiveMQ JMS Example - Java AutoConfiguration
---

* https://dzone.com/articles/how-to-integrate-spring-boot-and-apache-camel
  * https://github.com/chaitanya525/blog
* https://www.youtube.com/watch?v=B-Q_InvRvn0
* `Spring Boot Embedded ActiveMQ Configuration` https://memorynotfound.com/spring-boot-embedded-activemq-configuration-example

* `TemporaryQueueReplyManager` ( https://johnragan.wordpress.com/2010/01/11/jms-implementing-the-requestreply-pattern-and-other-newbie-practice-runs )

* `WildFly Camel User Guide` https://wildfly-extras.github.io/wildfly-camel/
* `Camel в вопросах и ответах` https://habr.com/company/redhatrussia/blog/352188/



НОРМАЛЬНО-1
---------

# JMS
activeMQConnectionFactory.brokerURL = vm://localhost?broker.persistent=false
pooledConnectionFactory.maxConnections = 15
pooledConnectionFactory.maximumActiveSessionPerConnection = 10
jmsConfiguration.concurrentConsumers = 10
jmsConfiguration.maxConcurrentConsumers = 20

# SERVICE MESSAGE
messageService.requestTimeout = 5000
messageService.timeToLive = 5000

# CLIENT MESSAGE
clientMessage.sentMessages = 7
clientMessage.responseDelay = 3000


НОРМАЛЬНО-2
---------

# JMS
activeMQConnectionFactory.brokerURL = vm://localhost?broker.persistent=false
pooledConnectionFactory.maxConnections = 100
pooledConnectionFactory.maximumActiveSessionPerConnection = 15
jmsConfiguration.concurrentConsumers = 10
jmsConfiguration.maxConcurrentConsumers = 20

# SERVICE MESSAGE
messageService.requestTimeout = 5000
messageService.timeToLive = 5000

# CLIENT MESSAGE
clientMessage.sentMessages = 10
clientMessage.responseDelay = 3000


НОРМАЛЬНО-3
---------

# JMS
activeMQConnectionFactory.brokerURL = vm://localhost?broker.persistent=false
pooledConnectionFactory.maxConnections = 100
pooledConnectionFactory.maximumActiveSessionPerConnection = 15
jmsConfiguration.concurrentConsumers = 15

# SERVICE MESSAGE
messageService.requestTimeout = 5000
messageService.timeToLive = 5000

# CLIENT MESSAGE
clientMessage.sentMessages = 15
clientMessage.responseDelay = 3000


НОРМАЛЬНО-4
---------

# JMS
activeMQConnectionFactory.brokerURL = vm://localhost?broker.persistent=false
pooledConnectionFactory.maxConnections = 100
pooledConnectionFactory.maximumActiveSessionPerConnection = 50
jmsConfiguration.concurrentConsumers = 50

# SERVICE MESSAGE
messageService.requestTimeout = 5000
messageService.timeToLive = 5000

# CLIENT MESSAGE
clientMessage.sentMessages = 49
clientMessage.responseDelay = 3000


НОРМАЛЬНО-5
---------

# JMS
activeMQConnectionFactory.brokerURL = vm://localhost?broker.persistent=false
pooledConnectionFactory.maxConnections = 100
pooledConnectionFactory.maximumActiveSessionPerConnection = 100
jmsConfiguration.concurrentConsumers = 50

# SERVICE MESSAGE
messageService.requestTimeout = 5000
messageService.timeToLive = 5000

# CLIENT MESSAGE
clientMessage.sentMessages = 50
clientMessage.responseDelay = 3000


НОРМАЛЬНО-6
---------

# JMS
activeMQConnectionFactory.brokerURL = vm://localhost?broker.persistent=false
pooledConnectionFactory.maxConnections = 100
pooledConnectionFactory.maximumActiveSessionPerConnection = 100
jmsConfiguration.concurrentConsumers = 90

# SERVICE MESSAGE
messageService.requestTimeout = 5000
messageService.timeToLive = 5000

# CLIENT MESSAGE
clientMessage.sentMessages = 90
clientMessage.responseDelay = 3000


НОРМАЛЬНО-7
---------

# JMS
activeMQConnectionFactory.brokerURL = vm://localhost?broker.persistent=false
pooledConnectionFactory.maxConnections = 200
pooledConnectionFactory.maximumActiveSessionPerConnection = 200
jmsConfiguration.concurrentConsumers = 190

# SERVICE MESSAGE
messageService.requestTimeout = 5000
messageService.timeToLive = 5000

# CLIENT MESSAGE
clientMessage.sentMessages = 175
clientMessage.responseDelay = 3000


НОРМАЛЬНО-8
---------

# JMS
activeMQConnectionFactory.brokerURL = vm://localhost?broker.persistent=false
pooledConnectionFactory.maxConnections = 200
pooledConnectionFactory.maximumActiveSessionPerConnection = 200
jmsConfiguration.concurrentConsumers = 190

# SERVICE MESSAGE
messageService.requestTimeout = 0
messageService.timeToLive = 0

# CLIENT MESSAGE
clientMessage.sentMessages = 500
clientMessage.responseDelay = 3000
clientMessage.allResponseDelay = 25000



------------------------------------------------------------------------------------------------------------------------

* `camelExchangeExample` https://examples.javacodegeeks.com/enterprise-java/apache-camel/apache-camel-exchange-example/
* `Apache Camel Hello World example` https://examples.javacodegeeks.com/enterprise-java/apache-camel/apache-camel-hello-world-example/
* `Camel ProducerTemplate and ConsumerTemplate Example` http://www.pretechsol.com/2013/10/camel-producertemplate-and_70.html
* `Spring JMS Example + ActiveMQ + Annotation/JavaConfig` https://www.devglan.com/spring-mvc/spring-jms-activemq-integration-example
* `Tutorial on Spring Remoting with JMS` http://people.apache.org/~dkulp/camel/tutorial-jmsremoting.html
* `How to start Apache Camel: Message Router with Spring Boot` https://grokonez.com/java-integration/start-apache-camel-message-router-spring-boot
* `How to test Apache Camel JMS routes with Spring and ActiveMQ step by step` https://medium.com/@mzimecki/how-to-test-apache-camel-jms-routes-with-spring-and-activemq-step-by-step-f429760074e6
* `Apache Camel ActiveMQ Example` https://examples.javacodegeeks.com/enterprise-java/apache-camel/apache-camel-activemq-example/
* `camel-jms` https://github.com/apache/camel/blob/master/components/camel-jms/src/test/java/org/apache/camel/component/jms/JmsInOutDisableTimeToLiveTest.java
* `Поддерживает ли Apache Camel использование подстановочных знаков activemq` https://stackoverrun.com/ru/q/6947598
* `IntelliJ Debug Configuration - Debugging Your Scala Application` http://allaboutscala.com/tutorials/chapter-1-getting-familiar-intellij-ide/intellij-debug-configuration-scala-application/
                                                                    https://github.com/Home-SCALA/allaboutscala
                                                                    https://github.com/nadimbahadoor/allaboutscala
* `Your first Scala Hello World application` http://allaboutscala.com/tutorials/chapter-1-getting-familiar-intellij-ide/scala-tutorial-first-hello-world-application/












------------------------------------------------------------------------------------------------------------------------

* **(** `D:\server\wildfly-10.1.0.Final\bin\standalone.bat -c standalone-full.xml` **)**
* **(** `http://localhost:8080/SpringCamelIntegration-0.0.1-SNAPSHOT/queue/test1/no` OR `http://localhost:8080/SpringCamelIntegration-0.0.1-SNAPSHOT/queue/test1/yes` **)**

```log
13:23:54,879 INFO  [org.apache.activemq.broker.BrokerService] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) ActiveMQ Message Broker (localhost, ID:DESKTOP-7C7R63S-51181-1548501768307-0:14) is shutting down
13:23:54,882 WARN  [org.apache.activemq.broker.jmx.ManagedRegionBroker] (ActiveMQ Task-2) Failed to register MBean: org.apache.activemq:BrokerName=localhost,Type=Topic,Destination=ActiveMQ.Advisory.Connection
13:23:54,883 WARN  [org.apache.activemq.broker.TransportConnection.Service] (ActiveMQ Task-2) Failed to remove connection ConnectionInfo {commandId = 1, responseRequired = true, connectionId = ID:DESKTOP-7C7R63S-51181-1548501768307-3:14, clientId = ID:DESKTOP-7C7R63S-51181-1548501768307-2:14, clientIp = vm://localhost#26, userName = null, password = *****, brokerPath = null, brokerMasterConnector = false, manageable = true, clientMaster = true, faultTolerant = false, failoverReconnect = false}, reason: java.lang.NoSuchMethodError: org.apache.activemq.thread.Scheduler.schedualPeriodically(Ljava/lang/Runnable;J)V
13:23:54,885 INFO  [org.apache.activemq.broker.BrokerService] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) ActiveMQ JMS Message Broker (localhost, ID:DESKTOP-7C7R63S-51181-1548501768307-0:14) stopped
13:23:54,886 ERROR [org.apache.camel.component.jms.DefaultJmsMessageListenerContainer] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) Could not refresh JMS Connection for destination 'test1:queue' - retrying using FixedBackOff{interval=5000, currentAttempts=12, maxAttempts=unlimited}. Cause: org.apache.activemq.thread.Scheduler.schedualPeriodically(Ljava/lang/Runnable;J)V
13:23:59,893 INFO  [org.apache.activemq.broker.BrokerService] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) Using Persistence Adapter: MemoryPersistenceAdapter
13:23:59,893 INFO  [org.apache.activemq.broker.BrokerService] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) ActiveMQ 5.14.3 JMS Message Broker (localhost) is starting
13:23:59,893 INFO  [org.apache.activemq.broker.BrokerService] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) For help or more information please see: http://activemq.apache.org/
13:23:59,896 INFO  [org.apache.activemq.broker.BrokerService] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) ActiveMQ JMS Message Broker (localhost, ID:DESKTOP-7C7R63S-51181-1548501768307-0:15) started
13:23:59,900 ERROR [org.apache.activemq.broker.BrokerService] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) Temporary Store limit is 50000 mb, whilst the temporary data directory: D:\server\wildfly-10.1.0.Final\bin\activemq-data\localhost\tmp_storage only has 18093 mb of usable space
13:23:59,900 INFO  [org.apache.activemq.broker.jmx.ManagementContext] (JMX connector) JMX consoles can connect to service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi
13:23:59,901 INFO  [org.apache.activemq.broker.TransportConnector] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) Connect13:26:21,539 INFO  [org.apache.activemq.broker.jmx.ManagementContext] (JMX connector) JMX consoles can connect to service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmior vm://localhost Started
13:23:59,912 INFO  [org.apache.activemq.broker.TransportConnector] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) Connector vm://localhost Stopped

13:26:16,508 INFO  [org.apache.activemq.broker.BrokerService] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) ActiveMQ Message Broker (localhost, ID:DESKTOP-7C7R63S-51181-1548501768307-0:42) is shutting down
13:26:16,512 WARN  [org.apache.activemq.broker.TransportConnection.Service] (ActiveMQ Task-1) Failed to remove connection ConnectionInfo {commandId = 1, responseRequired = true, connectionId = ID:DESKTOP-7C7R63S-51181-1548501768307-3:42, clientId = ID:DESKTOP-7C7R63S-51181-1548501768307-2:42, clientIp = vm://localhost#82, userName = null, password = *****, brokerPath = null, brokerMasterConnector = false, manageable = true, clientMaster = true, faultTolerant = false, failoverReconnect = false}, reason: org.apache.activemq.broker.BrokerStoppedException: Broker has been stopped: org.apache.activemq.broker.BrokerService$3@1784a77
13:26:16,513 INFO  [org.apache.activemq.broker.BrokerService] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) ActiveMQ JMS Message Broker (localhost, ID:DESKTOP-7C7R63S-51181-1548501768307-0:42) stopped
13:26:16,513 ERROR [org.apache.camel.component.jms.DefaultJmsMessageListenerContainer] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) Could not refresh JMS Connection for destination 'test1:queue' - retrying using FixedBackOff{interval=5000, currentAttempts=40, maxAttempts=unlimited}. Cause: org.apache.activemq.thread.Scheduler.schedualPeriodically(Ljava/lang/Runnable;J)V
13:26:21,524 INFO  [org.apache.activemq.broker.BrokerService] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) Using Persistence Adapter: MemoryPersistenceAdapter
13:26:21,525 INFO  [org.apache.activemq.broker.BrokerService] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) ActiveMQ 5.14.3 JMS Message Broker (localhost) is starting
13:26:21,525 INFO  [org.apache.activemq.broker.BrokerService] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) For help or more information please see: http://activemq.apache.org/
13:26:21,532 INFO  [org.apache.activemq.broker.BrokerService] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) ActiveMQ JMS Message Broker (localhost, ID:DESKTOP-7C7R63S-51181-1548501768307-0:43) started
13:26:21,536 ERROR [org.apache.activemq.broker.BrokerService] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) Temporary Store limit is 50000 mb, whilst the temporary data directory: D:\server\wildfly-10.1.0.Final\bin\activemq-data\localhost\tmp_storage only has 18093 mb of usable space
13:26:21,537 INFO  [org.apache.activemq.broker.TransportConnector] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) Connector vm://localhost Started
13:26:21,539 INFO  [org.apache.activemq.broker.jmx.ManagementContext] (JMX connector) JMX consoles can connect to service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi
13:26:21,550 INFO  [org.apache.activemq.broker.TransportConnector] (Camel (camel-1) thread #2 - JmsConsumer[test1:queue]) Connector vm://localhost Stopped



14:32:18,548 INFO  [io.undertow.servlet] (default task-1) Initializing Spring FrameworkServlet 'dispatcherServlet'
14:32:18,549 INFO  [org.springframework.web.servlet.DispatcherServlet] (default task-1) FrameworkServlet 'dispatcherServlet': initialization started
14:32:18,575 INFO  [org.springframework.web.servlet.DispatcherServlet] (default task-1) FrameworkServlet 'dispatcherServlet': initialization completed in 25 ms
14:32:24,568 ERROR [org.apache.camel.component.jms.DefaultJmsMessageListenerContainer] (Camel (camel-1) thread #1 - JmsConsumer[test1:queue]) Could not refresh JMS Connection for destination 'test1:queue' - retrying using FixedBackOff{interval=5000, currentAttempts=3, maxAttempts=unlimited}. Cause: Error while attempting to add new Connection to the pool; nested exception is javax.jms.JMSException: Could not connect to broker URL: tcp://localhost:61616. Reason: java.net.ConnectException: Connection refused: connect
14:32:30,573 ERROR [org.apache.camel.component.jms.DefaultJmsMessageListenerContainer] (Camel (camel-1) thread #1 - JmsConsumer[test1:queue]) Could not refresh JMS Connection for destination 'test1:queue' - retrying using FixedBackOff{interval=5000, currentAttempts=4, maxAttempts=unlimited}. Cause: Error while attempting to add new Connection to the pool; nested exception is javax.jms.JMSException: Could not connect to broker URL: tcp://localhost:61616. Reason: java.net.ConnectException: Connection refused: connect

14:26:37,681 WARN  [org.apache.camel.component.jms.reply.TemporaryQueueReplyManager] (Thread-664) Exception inside the DMLC for Temporary ReplyTo Queue for destination test1:queue, refreshing ReplyTo destination: javax.jms.JMSException: Error while attempting to add new Connection to the pool
	at org.apache.activemq.jms.pool.PooledConnectionFactory.createJmsException(PooledConnectionFactory.java:266)
	at org.apache.activemq.jms.pool.PooledConnectionFactory.createConnection(PooledConnectionFactory.java:225)
	at org.apache.activemq.jms.pool.PooledConnectionFactory.createConnection(PooledConnectionFactory.java:204)
	at org.springframework.jms.support.JmsAccessor.createConnection(JmsAccessor.java:180)
	at org.springframework.jms.listener.AbstractJmsListeningContainer.createSharedConnection(AbstractJmsListeningContainer.java:413)
	at org.springframework.jms.listener.AbstractJmsListeningContainer.establishSharedConnection(AbstractJmsListeningContainer.java:381)
	at org.springframework.jms.listener.DefaultMessageListenerContainer.establishSharedConnection(DefaultMessageListenerContainer.java:803)
	at org.springframework.jms.listener.AbstractJmsListeningContainer.doStart(AbstractJmsListeningContainer.java:285)
	at org.springframework.jms.listener.AbstractJmsListeningContainer.start(AbstractJmsListeningContainer.java:270)
	at org.springframework.jms.listener.DefaultMessageListenerContainer.start(DefaultMessageListenerContainer.java:598)
	at org.apache.camel.component.jms.reply.ReplyManagerSupport.doStart(ReplyManagerSupport.java:248)
	at org.apache.camel.support.ServiceSupport.start(ServiceSupport.java:61)
	at org.apache.camel.util.ServiceHelper.startService(ServiceHelper.java:74)
	at org.apache.camel.util.ServiceHelper.startService(ServiceHelper.java:59)
	at org.apache.camel.component.jms.JmsProducer.createReplyManager(JmsProducer.java:547)
	at org.apache.camel.component.jms.JmsProducer.initReplyManager(JmsProducer.java:105)
	at org.apache.camel.component.jms.JmsProducer.processInOut(JmsProducer.java:185)
	at org.apache.camel.component.jms.JmsProducer.process(JmsProducer.java:150)
	at org.apache.camel.processor.CamelInternalProcessor.process(CamelInternalProcessor.java:191)
	at org.apache.camel.util.AsyncProcessorHelper.process(AsyncProcessorHelper.java:109)
	at org.apache.camel.processor.UnitOfWorkProducer.process(UnitOfWorkProducer.java:68)
	at org.apache.camel.impl.ProducerCache$2.doInProducer(ProducerCache.java:375)
	at org.apache.camel.impl.ProducerCache$2.doInProducer(ProducerCache.java:343)
	at org.apache.camel.impl.ProducerCache.doInProducer(ProducerCache.java:233)
	at org.apache.camel.impl.ProducerCache.sendExchange(ProducerCache.java:343)
	at org.apache.camel.impl.ProducerCache.send(ProducerCache.java:201)
	at org.apache.camel.impl.DefaultProducerTemplate.send(DefaultProducerTemplate.java:128)
	at org.apache.camel.impl.DefaultProducerTemplate.sendBody(DefaultProducerTemplate.java:132)
	at org.apache.camel.impl.DefaultProducerTemplate.sendBody(DefaultProducerTemplate.java:149)
	at org.apache.camel.impl.DefaultProducerTemplate.requestBody(DefaultProducerTemplate.java:301)
	at com.mkyong.service.MessageService.inOut(MessageService.java:60)
	at com.mkyong.service.MessageService.sendMessage(MessageService.java:50)
	at com.mkyong.controller.MessageController.lambda$taskSendMessage$0(MessageController.java:62)
	at java.lang.Thread.run(Thread.java:748)
Caused by: javax.jms.JMSException: Could not connect to broker URL: tcp://localhost:61616. Reason: java.net.ConnectException: Connection refused: connect
```
