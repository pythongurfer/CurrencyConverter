package com.example.currencyconverter.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data // Lombok: generated getters setters, toString(), etc
@Document(collection = "conversions") // marks this class as a MongoDB document
public class Conversion {
    @Id
    private String id;

    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal amount;
    private BigDecimal exchangeRate;
    private BigDecimal convertedAmount;

}
