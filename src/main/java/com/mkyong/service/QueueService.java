package com.mkyong.service;

import com.mkyong.jms.support.IntegrationBus;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.jms.JMSException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class QueueService {

    private static final Logger LOGGER = LogManager.getLogger(QueueService.class);

	@Autowired
	private IntegrationBus integrationBus;

//	@Autowired
//	@Qualifier("producerTemplate")
//	private ProducerTemplate producerTemplate;

	public String sendMessage(String queue, String message) {
		try {
			return integrationBus.inOut(queue, message);
		} catch (CamelExecutionException|JMSException ex) {
			//TODO:  Caused by: org.apache.camel.ExchangeTimedOutException: The OUT message was not received within: 5000 millis due reply message with correlationID: Camel-ID-DESKTOP-7C7R63S-61461-1547363264078-0-15 not received on destination: temp-queue://ID:DESKTOP-7C7R63S-61456-1547363263660-5:2:1. Exchange[Message: Calling via Spring Boot Rest Controller]
//			LOGGER.error("|<<   {}", ex); //TODO:  org.apache.camel.CamelExecutionException: Exception occurred during execution on the exchange: Exchange[Message: Calling via Spring Boot Rest Controller]
		}
		return null;
	}
}
