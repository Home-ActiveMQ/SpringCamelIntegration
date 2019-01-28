package com.mkyong.jms.support;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;

import static com.mkyong.util.JmsUtil.JMS;

@Service
@ConfigurationProperties("integrationBus")
public class IntegrationBus {

    private static final Logger LOGGER = LogManager.getLogger(IntegrationBus.class);

	private CamelContext camelContext;

	private String requestTimeout;

	private String timeToLive;

	@Autowired
	public IntegrationBus(CamelContext camelContext) {
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

	public <T> T inOut(String queue, T message) throws JMSException {
		ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
		return (T) producerTemplate.requestBody(JMS + ":" + queue + ":queue?exchangePattern=InOut&requestTimeout=" + requestTimeout + "&timeToLive=" + timeToLive, message);
	}
}
