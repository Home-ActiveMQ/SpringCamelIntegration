
* `spring-boot-starter` https://www.mkyong.com/spring-boot/spring-boot-non-web-application-example
* `Log4j2 xml configuration example` https://howtodoinjava.com/log4j2/log4j-2-xml-configuration-example
* `Spring Boot @ConfigurationProperties example` https://www.mkyong.com/spring-boot/spring-boot-configurationproperties-example

Spring Boot with Camel ActiveMQ JMS Example - Java AutoConfiguration
---

* https://dzone.com/articles/how-to-integrate-spring-boot-and-apache-camel
  * https://github.com/chaitanya525/blog
* https://www.youtube.com/watch?v=B-Q_InvRvn0



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

