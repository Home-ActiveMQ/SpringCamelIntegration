
* `spring-boot-starter` https://www.mkyong.com/spring-boot/spring-boot-non-web-application-example
* `Log4j2 xml configuration example` https://howtodoinjava.com/log4j2/log4j-2-xml-configuration-example
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













