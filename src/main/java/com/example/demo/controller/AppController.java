package com.example.demo.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

	private static Logger LOG = LoggerFactory.getLogger(AppController.class);

	@PostMapping("api/msg")
	public ResponseEntity<Object> getMsg(@RequestHeader Map<String, String> headers, @RequestBody String postedData) {

		String challenge = "";
		System.out.println("SYSO: getMsg was trigger");
		LOG.debug("getMsg was trigger");
		LOG.debug(postedData);

		for (Map.Entry<String, String> element : headers.entrySet()) {
			LOG.debug(element.getKey() + " / " + element.getValue());
			if (element.getKey().equalsIgnoreCase("smartsheet-hook-challenge")) {
				challenge = element.getValue();
				break;
			}
		}

		LOG.debug("challenge: " + challenge);

		if (!challenge.isBlank()) {
			return ResponseEntity.status(HttpStatus.OK).body("{\"smartsheetHookResponse\": \"" + challenge + "\"}");
		}
		return ResponseEntity.status(HttpStatus.OK).body(postedData);
	}
}
