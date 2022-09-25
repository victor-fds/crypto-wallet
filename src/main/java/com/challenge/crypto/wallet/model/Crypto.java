package com.challenge.crypto.wallet.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.Generated;

@Generated
@Data
public class Crypto {
	private String symbol;
	private double quantity;
	private BigDecimal buyPrice;
	private BigDecimal updatedPrice;
}
