package com.mkyong.controller;

import com.mkyong.dto.TokenLifecycleRequestDto;
import com.mkyong.dto.TokenLifecycleResponseDto;
import com.mkyong.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1. >> `crypto/client/encrypt` >> used LoggingClientHttpRequestInterceptor >> wildfly-10.1.0/standalone/log/server.log
 * 2. << `crypto/encrypt`
 *
 *    { "restTemplate" : "221907021951" } { "threadId" : "582"} << { "threadId" : "586"}
 */

@RestController
@RequestMapping("crypto")
public class CryptoController {

	@Autowired
	private CryptoService cryptoService;

	/**
	 * http://localhost:8080/SpringCamelIntegration-0.0.1-SNAPSHOT/crypto/client/encrypt
	 */
	@RequestMapping("client/encrypt")
	public String clientEncrypt() {
		return cryptoService.encrypt("crypto/encrypt");
	}

	/**
	 * http://localhost:8080/SpringCamelIntegration-0.0.1-SNAPSHOT/crypto/encrypt
	 */
	@RequestMapping("encrypt")
	public String encrypt() {
		return "{ \"threadId\" : \"" + Thread.currentThread().getId() + "\"} "; //TODO:  some data...
	}

	/**
	 * http://localhost:8080/SpringCamelIntegration-0.0.1-SNAPSHOT/crypto/client/lifecycle
	 */
	@RequestMapping("client/lifecycle")
	public ResponseEntity<?> clientLifecycle() {
		TokenLifecycleRequestDto req = new TokenLifecycleRequestDto();
//		req.setOperationReason("bla bla bla");
		req.setOperationReasonCode("ACCOUNT_UPDATE");
		req.setOperationType("UPDATE");
		req.setOperatorID("operator");

		TokenLifecycleResponseDto lifecycleResponseDto = cryptoService.lifecycle("crypto/lifecycle", req);
		return lifecycleResponseDto.getErrorCode()!=null
				? new ResponseEntity<>(lifecycleResponseDto, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * http://localhost:8080/SpringCamelIntegration-0.0.1-SNAPSHOT/crypto/lifecycle
	 *
	 * {"operationReasonCode":"ACCOUNT_UPDATE","operatorID":"operator","operationType":"UPDATE"}
	 */
	@PostMapping("lifecycle")
	public TokenLifecycleResponseDto lifecycle(@RequestBody TokenLifecycleRequestDto request) {
		TokenLifecycleResponseDto lifecycleResponseDto = new TokenLifecycleResponseDto();
		lifecycleResponseDto.setErrorCode(request.getOperationReason()); //TODO:  'bla bla bla'
		return lifecycleResponseDto;
	}
}
