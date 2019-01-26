package com.mkyong.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mkyong.config.properties.ClientMessageProperties;
import com.mkyong.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("queue")
public class MessageController {

	private static final Logger LOGGER = LogManager.getLogger(MessageController.class);

	private AtomicInteger deliveredMessages;

	private AtomicInteger lostMessages;

	static List<String> allLostMessages = new ArrayList<>();

	@Autowired
	private ClientMessageProperties clientMessageProperties;

	@Autowired
	private MessageService messageService;

	private final String runCamelTest = "yes";

	/**
	 * http://localhost:8080/SpringCamelIntegration-0.0.1-SNAPSHOT/queue/test1/no
	 * http://localhost:8080/SpringCamelIntegration-0.0.1-SNAPSHOT/queue/test1/yes
	 */
	@RequestMapping("test1/{readComand}")
	public String test1(@PathVariable String readComand) throws InterruptedException {
		boolean isRunCamelTest = runCamelTest.equals(readComand);
		if (isRunCamelTest) {
			deliveredMessages = new AtomicInteger();
			lostMessages = new AtomicInteger();
			for (int message = 1; message <= clientMessageProperties.getSentMessages(); message++) {
				new Thread(taskSendMessage(message)).start();
				synchronized (allLostMessages) {
					allLostMessages.add("" + message);
				}
			}

			Thread.sleep(clientMessageProperties.getAllResponseDelay());
			return ">>>>>>>>>>>>>>>>>>>>>>>> SENT MESSAGES = " + clientMessageProperties.getSentMessages() + ";       DELIVERED MESSAGES = " + deliveredMessages + ";       LOST MESSAGES = " + lostMessages + "; <<<<<<<<<<<<<<<<<<<<<<<<";
		}
		return ":test1:queue";
	}

	private Runnable taskSendMessage(Object message) {
		return () -> {
			LOGGER.debug(" >>|  {}", message);
			String response = messageService.sendMessage("" + message);
			if (response!=null) {
				LOGGER.debug("|<<   {}", response);
				deliveredMessages.incrementAndGet();
				synchronized (allLostMessages) { allLostMessages.remove(response); }
			} else {
				LOGGER.error("|<<   {}", response);
				lostMessages.incrementAndGet();
			}
		};
	}
}
