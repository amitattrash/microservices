package com.microservices.currencyexchangeservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.currencyexchangeservice.beans.CurrencyExchange;
import com.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
@RestController
public class CurrencyExchangeController {
	
	@Autowired
	CurrencyExchangeRepository currencyExchangeRepository;
	@Autowired
	Environment environment;
	
	@GetMapping( "/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange getCurrencyExchange(@PathVariable String from, @PathVariable String to) {
		
		CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
		currencyExchange.setEnvironment(environment.getProperty("local.server.port"));
		return currencyExchange;
	}

}
