package com.mkyong.jms.routes;

import com.mkyong.entity.Bank;
import com.mkyong.service.BankService;
import com.mkyong.jms.processor.TestProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import static com.mkyong.util.JmsUtil.JMS;

/**
 * @see https://www.baeldung.com/spring-depends-on
 * @see https://www.concretepage.com/spring/example_annotationconfigapplicationcontext_spring
 *      https://www.ibm.com/developerworks/ru/library/ws-springjava/index.html
 */
@Component
//@DependsOn({"test1Processor", "test2Processor", "test3Processor"})
@DependsOn({"multiBankConfig"})
public class QueueRoutes extends RouteBuilder {

	@Value("${jmsConfiguration.concurrentConsumers:1}")
	private int concurrentConsumers;

	@Autowired
	private ApplicationContext applicationContext;

	private ConfigurableListableBeanFactory beanFactory;

	@Autowired
	private BankService bankService;

    @PostConstruct
    public void init() {
        beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
    }

	@Override
	public void configure() throws Exception {
		if (0<bankService.count()) {
			for (Bank bank : bankService.findAll())
				from(JMS + ":" + bank.getUniqueUrl() + ":queue?concurrentConsumers=" + concurrentConsumers)
						.routeId(bank.getUniqueUrl())
						.process((TestProcessor) beanFactory.getBean(bank.getUniqueUrl() + "Processor"));
		} else {
			from(JMS + ":test:queue?concurrentConsumers=" + concurrentConsumers)
					.routeId("test")
					.process((TestProcessor) beanFactory.getBean("testProcessor"));
		}
	}
}
