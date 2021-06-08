package com.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservices.currencyconversionservice.beans.CurrencyConversion;
import com.microservices.currencyconversionservice.util.CurrencyExchangeProxy;
@RestController
public class CurrencyConversionController {
	@Autowired
	CurrencyExchangeProxy currencyExchangeProxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getCurrencyConversion(@PathVariable String from , @PathVariable String to, @PathVariable BigDecimal quantity) {
		HashMap<String, String> uriVariablesMap = new HashMap<>();
		System.out.println("from:"+from+"   to:"+to);
		uriVariablesMap.put("from", from);
		uriVariablesMap.put("toCurrency", to);
		ResponseEntity<CurrencyConversion> currencyConversion = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{toCurrency}", CurrencyConversion.class,
				uriVariablesMap);
		CurrencyConversion response = currencyConversion.getBody();
		response.setQuantity(quantity);
		response.setTotalLoanAmount(response.getConversionMultiple().multiply(response.getQuantity()));
		return response;
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getCurrencyConversionFeign(@PathVariable String from , @PathVariable String to, @PathVariable BigDecimal quantity) {
		CurrencyConversion retrieveCurrencyExchange = currencyExchangeProxy.retrieveCurrencyExchange(from, to);
		retrieveCurrencyExchange.setQuantity(quantity);
		retrieveCurrencyExchange.setTotalLoanAmount(quantity.multiply(retrieveCurrencyExchange.getConversionMultiple()));
		return retrieveCurrencyExchange;
	}
}
