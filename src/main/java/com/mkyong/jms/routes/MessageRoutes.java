package com.mkyong.jms.routes;

import com.mkyong.jms.processor.MessageProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import static com.mkyong.util.JmsUtil.JMS;

@Component
public class MessageRoutes extends RouteBuilder {

	@Value("${jmsConfiguration.concurrentConsumers:1}")
	private int concurrentConsumers;

	@Autowired
	private MessageProcessor messageProcessor;

	@Override
	public void configure() throws Exception {
		from(JMS + ":queue:message?concurrentConsumers=" + concurrentConsumers)
				.routeId("message")
				.process(messageProcessor);
	}
}
