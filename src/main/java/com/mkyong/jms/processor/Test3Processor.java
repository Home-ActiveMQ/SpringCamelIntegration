package com.mkyong.jms.processor;

import com.mkyong.config.properties.ClientMessageProperties;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Test3Processor implements Processor {

	private static final Logger LOGGER = LogManager.getLogger(Test3Processor.class);

	@Autowired
	private ClientMessageProperties clientMessageProperties;

	@Override
	public void process(Exchange exchange) throws Exception {
//		LOGGER.debug(">>|  {}", "'" + exchange.getPattern().toString() + "' " + message);
		Object message = exchange.getIn().getBody();
		LOGGER.debug( " >>|  {}", message);
		Thread.sleep(clientMessageProperties.getResponseDelay());
		LOGGER.debug("|<<   {}", message);
		exchange.getIn().setBody(message);
	}
}
