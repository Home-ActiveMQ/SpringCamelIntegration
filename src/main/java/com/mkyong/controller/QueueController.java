package com.mkyong.controller;

import com.mkyong.service.BankService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mkyong.config.properties.ClientMessageProperties;
import com.mkyong.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("queue")
public class QueueController {

	private static final Logger LOGGER = LogManager.getLogger(QueueController.class);

	private AtomicInteger deliveredMessages;

    private AtomicInteger lostMessages;

	static List<String> allLostMessages = new ArrayList<>();

	@Autowired
	private ClientMessageProperties clientMessageProperties;

	@Autowired
	private QueueService queueService;


    @Autowired
    private BankService bankService;

    /**
     * http://localhost:8080/SpringCamelIntegration-0.0.1-SNAPSHOT/queue/
     */
    @RequestMapping("/")
    public ResponseEntity<String> index() {
        if (0<bankService.count()) {
            return new ResponseEntity<>("<ul><li><a href=\"test1\">queue/test1</a></li><li><a href=\"test2\">queue/test2</a></li><li><a href=\"test3\">queue/test3</a></li></ul>", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("<ul><li><a href=\"test\">queue/test</a></li></ul>", HttpStatus.OK);
        }
    }

	/**
	 * http://localhost:8080/SpringCamelIntegration-0.0.1-SNAPSHOT/queue/test1
	 */
	@RequestMapping("/{queue}")
    public ResponseEntity<String> queue(@PathVariable String queue) throws InterruptedException {
        deliveredMessages = new AtomicInteger();
        lostMessages = new AtomicInteger();
        int sentMessage = 0;

        if (StringUtils.isNotBlank(queue)) {
            while (sentMessage < clientMessageProperties.getSentMessages()) {
                sentMessage++;
                new Thread(taskSendMessage(queue, String.valueOf(sentMessage))).start();
                synchronized (allLostMessages) {
                    allLostMessages.add(String.valueOf(sentMessage));
                }
            }
            Thread.sleep(clientMessageProperties.getAllResponseDelay());
        }

        return new ResponseEntity<>(">>>>>>>>>>>>>>>>>>>>>>>> SENT MESSAGES = " + sentMessage + ";       DELIVERED MESSAGES = " + deliveredMessages + ";       LOST MESSAGES = " + (sentMessage-deliveredMessages.get()) + " (" + lostMessages + "); <<<<<<<<<<<<<<<<<<<<<<<<", HttpStatus.OK);
	}

    private Runnable taskSendMessage(String queue, String message) {
        return () -> {
            LOGGER.debug(" >>|  {}", message);
            String response = queueService.sendMessage(queue, message);
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
