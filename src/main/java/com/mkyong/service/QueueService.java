package com.mkyong.service;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import javax.jms.JMSException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static com.mkyong.util.JmsUtil.JMS;

@Service
@ConfigurationProperties("messageService")
public class QueueService {

    private static final Logger LOGGER = LogManager.getLogger(QueueService.class);

	private CamelContext camelContext;

	private String requestTimeout;

	private String timeToLive;

	@Autowired
	public QueueService(CamelContext camelContext) {
		this.camelContext = camelContext;
	}

//	@Autowired
//	@Qualifier("producerTemplate")
//	private ProducerTemplate producerTemplate;

	public String getRequestTimeout() {
		return requestTimeout;
	}

	public void setRequestTimeout(String requestTimeout) {
		this.requestTimeout = requestTimeout;
	}

	public String getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(String timeToLive) {
		this.timeToLive = timeToLive;
	}

	public String sendMessage(String message) {
		try {
			return inOut(message);
		} catch (CamelExecutionException|JMSException ex) {
			//TODO:  Caused by: org.apache.camel.ExchangeTimedOutException: The OUT message was not received within: 5000 millis due reply message with correlationID: Camel-ID-DESKTOP-7C7R63S-61461-1547363264078-0-15 not received on destination: temp-queue://ID:DESKTOP-7C7R63S-61456-1547363263660-5:2:1. Exchange[Message: Calling via Spring Boot Rest Controller]
//			LOGGER.error("|<<   {}", ex); //TODO:  org.apache.camel.CamelExecutionException: Exception occurred during execution on the exchange: Exchange[Message: Calling via Spring Boot Rest Controller]
		}
		return null;
	}

	private <T> T inOut(T message) throws JMSException {
		ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
		return (T) producerTemplate.requestBody(JMS + ":test1:queue?exchangePattern=InOut&requestTimeout=" + requestTimeout + "&timeToLive=" + timeToLive, message);
	}
}
