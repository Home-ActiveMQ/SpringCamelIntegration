package com.mkyong.jms.processor;

import com.mkyong.config.properties.ClientMessageProperties;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class TestProcessor implements Processor {

	private static final Logger LOGGER = LogManager.getLogger(TestProcessor.class);

	private String name;

//	@Autowired
//	private ClientMessageProperties clientMessageProperties;

	public TestProcessor() {}

	public TestProcessor(String name) {
		this.name = "(" + name + ")";
	}

	@Override
	public void process(Exchange exchange) throws Exception {
//		LOGGER.debug(">>|  {}", "'" + exchange.getPattern().toString() + "' " + message);
		Object message = exchange.getIn().getBody();
		LOGGER.debug( " >>|  {}  {}", message, name);
		Thread.sleep(3000L); //FIXME:  Thread.sleep(clientMessageProperties.getResponseDelay());
		LOGGER.debug("|<<   {}  {}", message, name);
		exchange.getIn().setBody(message);
	}
}
