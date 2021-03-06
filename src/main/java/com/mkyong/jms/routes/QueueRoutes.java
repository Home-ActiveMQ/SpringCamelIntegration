package com.mkyong.jms.routes;

import com.mkyong.jms.processor.*;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import static com.mkyong.util.JmsUtil.JMS;

@Component
public class QueueRoutes extends RouteBuilder {

	@Value("${jmsConfiguration.concurrentConsumers:1}")
	private int concurrentConsumers;

	@Autowired
	private Test1Processor test1Processor;

	@Autowired
	private Test2Processor test2Processor;

	@Autowired
	private Test3Processor test3Processor;

	@Override
	public void configure() throws Exception {
		from(JMS + ":test1:queue?concurrentConsumers=" + concurrentConsumers)
				.routeId("test1")
				.process(test1Processor);

		from(JMS + ":test2:queue?concurrentConsumers=" + concurrentConsumers)
				.routeId("test2")
				.process(test2Processor);

		from(JMS + ":test3:queue?concurrentConsumers=" + concurrentConsumers)
				.routeId("test3")
				.process(test3Processor);
	}
}
