package com.challenge.crypto.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.crypto.wallet.service.CryptoParserService;

@RestController
public class MarketSearchController {

	@Autowired
	private CryptoParserService cryptoService;
	

	@RequestMapping(value="/", method=RequestMethod.GET)
	public ResponseEntity<String> home() {
		
		
		return new ResponseEntity<>("Results generated sucessfully", HttpStatus.OK);
	}
}
