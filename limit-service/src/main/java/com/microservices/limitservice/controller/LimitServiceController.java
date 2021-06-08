package com.microservices.limitservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.limitservice.beans.Limit;
import com.microservices.limitservice.configuration.LimitServiceConfiguration;
@RestController
public class LimitServiceController {
	@Autowired
	private LimitServiceConfiguration limitConfiguration;
	@GetMapping("/limit")
	public Limit getLimits() {
		return new Limit(limitConfiguration.getMinimum(),limitConfiguration.getMaximum());
	}

}
