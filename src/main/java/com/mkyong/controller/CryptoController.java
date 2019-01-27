package com.mkyong.controller;

import com.mkyong.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
