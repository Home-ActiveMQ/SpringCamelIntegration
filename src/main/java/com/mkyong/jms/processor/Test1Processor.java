package com.mkyong.jms.processor;

import com.google.gson.Gson;
import com.mkyong.config.properties.ClientMessageProperties;
import com.mkyong.data.MessageTest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class Test1Processor implements Processor {

	private static final Logger LOGGER = LogManager.getLogger(Test1Processor.class);

	@Autowired
	private ClientMessageProperties clientMessageProperties;

	@Override
	public void process(Exchange exchange) throws Exception {
//		LOGGER.debug(">>|  {}", "'" + exchange.getPattern().toString() + "' " + obj);
		Object obj = exchange.getIn().getBody();
		MessageTest message = new Gson().fromJson(String.valueOf(obj), MessageTest.class);
		LOGGER.debug( " >>|  {}", message);

		Thread.sleep(clientMessageProperties.getResponseDelay());

		message = new Gson().fromJson(String.valueOf(obj), MessageTest.class);
		LOGGER.debug("|<<   {}", message);
		exchange.getIn().setBody(obj);
	}
}
