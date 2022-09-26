package com.challenge.crypto.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.Setter;

@Data
@AllArgsConstructor
public class Crypto {
	private String symbol;
	private Double quantity;
	private Double buyPrice;
	private Double updatedPrice;
}
