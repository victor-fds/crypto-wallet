package com.challenge.crypto.wallet.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.crypto.wallet.api.CoinCapApi;
import com.challenge.crypto.wallet.model.Crypto;

@Service
public class CryptoParserService {
	
	private CoinCapApi coinApi;
	@Autowired
	public CryptoParserService(CoinCapApi coinApi) {
		this.coinApi = coinApi;
	}
	
	public String getBestAsset(List<Crypto> cryptoList) {
		Double total = 0d;
		HashMap<String, Crypto> output = new HashMap<>();

		double bestPerformance =  Double.MIN_VALUE;
		double worstPerformance =  Double.MAX_VALUE;
		
		for(Crypto crypto: cryptoList) {
			double currentPerformance = crypto.getUpdatedPrice() / crypto.getBuyPrice();
			total = total + crypto.getQuantity() * crypto.getUpdatedPrice();
			
			if(currentPerformance > bestPerformance) {
				bestPerformance = currentPerformance;
				output.put("bestAsset", crypto);
			}
			
			if(currentPerformance < worstPerformance) {
				worstPerformance = currentPerformance;
				output.put("worstAsset", crypto);
			}
		}
		
		return String.format(Locale.US, "total=%.2f,best_asset=%s,best_performance=%.2f,worst_asset=%s,worst_performance=%.2f", 
				total, output.get("bestAsset").getSymbol(), bestPerformance, output.get("worstAsset").getSymbol(), worstPerformance);
	}
	
	public List<Crypto> loadCriptosFromCsv() {
		List<Crypto> cryptoList = new ArrayList<>();
		
		try {
			Scanner in = new Scanner(new File("C:\\Users\\victo\\eclipse-workspace\\wallet\\src\\main\\resources\\static\\coins.csv"));
			
			if(in.hasNextLine())
				in.nextLine(); 
			
			while(in.hasNextLine()) {
			    String line = in.nextLine();
			    String[] csvSplit = line.split(",");
			    
			    Crypto crypto = new Crypto(csvSplit[0], Double.valueOf(csvSplit[1]), Double.valueOf(csvSplit[2]), -1d);
			    cryptoList.add(crypto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cryptoList;
	}
	
	
	public Crypto getCryptoValuesFromApi(Crypto crypto) throws InterruptedException {
		System.out.println("Submitted request " + crypto.getSymbol() + " at " + LocalDateTime.now());
		JSONObject result = this.coinApi.getAssetOnInterval(crypto.getSymbol());
		crypto.setUpdatedPrice(Double.parseDouble(result.getString("priceUsd")));
		//TimeUnit.SECONDS.sleep(3);
		System.out.println("Completed request " + crypto.getSymbol() + " at " + LocalDateTime.now());
		return crypto;
	}
}
