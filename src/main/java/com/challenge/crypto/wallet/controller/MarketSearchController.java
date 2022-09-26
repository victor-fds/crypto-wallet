package com.challenge.crypto.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.crypto.wallet.service.CryptoParserService;
import com.challenge.crypto.wallet.service.ThreadedService;

@RestController
public class MarketSearchController {

	@Autowired
	private ThreadedService threads;

	@RequestMapping(value="/crypto-results", method=RequestMethod.GET)
	public ResponseEntity<String> home() {
		String cryptoResults = threads.getCryptoResults();
		return new ResponseEntity<>(cryptoResults, HttpStatus.OK);
	}
}
