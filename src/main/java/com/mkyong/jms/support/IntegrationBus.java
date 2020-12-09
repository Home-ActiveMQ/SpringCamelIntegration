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

	/**
	 * @see https://access.redhat.com/documentation/en-us/red_hat_jboss_fuse/6.2.1/html/apache_camel_component_reference/idu-jms
	 *
	 * Producer only: The timeout for waiting for a reply when using the InOut Exchange Pattern (in milliseconds).
	 * The default is 20 seconds.
	 * From Camel 2.13/2.12.3 onwards you can include the header CamelJmsRequestTimeout to override this endpoint configured timeout value, and thus have per message individual timeout values.
	 */
	public <T> T inOut(String queue, T message) throws JMSException {
		ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
//		return (T) producerTemplate.requestBody(JMS + ":" + queue + ":queue?exchangePattern=InOut&requestTimeout=" + requestTimeout + "&timeToLive=" + timeToLive, message);

		return (T) producerTemplate.requestBody(JMS + ":" + queue + ":queue?exchangePattern=InOut&requestTimeout=" + requestTimeout + "&timeToLive=" + timeToLive + "&disableReplyTo=false", message);

//		return (T) producerTemplate.requestBody(JMS + ":" + queue + ":queue?exchangePattern=InOut&requestTimeout=" + requestTimeout, message);
//		return (T) producerTemplate.requestBody(JMS + ":" + queue + ":queue?exchangePattern=InOut&requestTimeout=" + requestTimeout + "&disableReplyTo=false&timeToLive=20", message);
//		return (T) producerTemplate.requestBody(JMS + ":" + queue + ":queue?exchangePattern=InOut&requestTimeout=" + requestTimeout + "&disableReplyTo=false&timeToLive=200", message);
//				return (T) producerTemplate.requestBody(JMS + ":" + queue + ":queue?exchangePattern=InOut&requestTimeout=" + requestTimeout + "&disableReplyTo=false&timeToLive=1", message); // TODO 10
//		return (T) producerTemplate.requestBody(JMS + ":" + queue + ":queue?exchangePattern=InOut&requestTimeout=" + requestTimeout + "&disableReplyTo=false&timeToLive=5", message); // TODO 10
//		return (T) producerTemplate.requestBody(JMS + ":" + queue + ":queue?exchangePattern=InOut&requestTimeout=" + requestTimeout + "&disableReplyTo=false&timeToLive=8", message); // TODO 10000
//		return (T) producerTemplate.requestBody(JMS + ":" + queue + ":queue?exchangePattern=InOut&requestTimeout=" + requestTimeout + "&disableReplyTo=false&timeToLive=10000", message);
	}
}
