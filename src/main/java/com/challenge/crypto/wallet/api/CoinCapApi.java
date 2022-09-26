package com.challenge.crypto.wallet.api;

import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CoinCapApi {

	@Autowired
    private RestTemplate restTemplate;
    
	public JSONObject getAssetOnInterval(String assetName) {
		String crypto = getAssetId(assetName);
		JSONArray cryptoArray = new JSONObject(crypto).getJSONArray("data");
		
		if(cryptoArray == null) {
			return new JSONObject("{}");
		}
		
		JSONObject cryptoJson = cryptoArray.getJSONObject(0);
		
		if(cryptoJson != null && !cryptoJson.isEmpty()) {
			String assetId = cryptoJson.getString("id");
			
	        String url = "https://api.coincap.io/v2/assets/" + assetId + "/history" +
	        		"?interval=d1&start=1617753600000&end=1617753601000";
	        HttpHeaders headers = new HttpHeaders();

	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	        
	        String assetOnPeriod = this.restTemplate.getForObject(url, String.class);  
	        
	        if(assetOnPeriod != null) {
	    		JSONObject asset = new JSONObject(assetOnPeriod).getJSONArray("data").getJSONObject(0);
	    		return asset;
	        }
		}
		return new JSONObject("{}");
	}
	
	private String getAssetId(String assetName) {
        String url = "https://api.coincap.io/v2/assets?search="+ assetName;

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        
        return this.restTemplate.getForObject(url, String.class);          
	}
		
}
