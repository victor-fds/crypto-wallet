package com.challenge.crypto.wallet.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.crypto.wallet.model.Crypto;

@Service
public class ThreadedService {

	private static final ExecutorService pool = Executors.newFixedThreadPool(3);
	private CryptoParserService cryptoParser;
	
	@Autowired
	public ThreadedService(CryptoParserService cryptoParser) {
		this.cryptoParser = cryptoParser;
	}
	
   public Callable<Crypto> getData(Crypto copy) {
       return new Callable<Crypto>() {
           @Override
           public Crypto call() throws Exception {
        	   return cryptoParser.getCryptoValuesFromApi(copy);
           }
       };
   }
	
	public String getCryptoResults() {
		System.out.println("Starting pool request at " + LocalDateTime.now());
		List<Crypto> cryptoList = cryptoParser.loadCriptosFromCsv();
		
		List<Future<Crypto>> futureList = new ArrayList<>();
		
		for(Crypto crypto: cryptoList) {
			Crypto copy = crypto;
			Callable<Crypto> callable = getData(copy);
			futureList.add(pool.submit(callable));
		}
		
		int i=0;
		for(Future<Crypto> future: futureList) {
			try {
				Crypto copy = cryptoList.get(i);
				Crypto cryptoFromApi = future.get();
				copy.setUpdatedPrice(cryptoFromApi.getUpdatedPrice());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("Completed pool request at " + LocalDateTime.now());
		return cryptoParser.getBestAsset(cryptoList);
	}
	

}
