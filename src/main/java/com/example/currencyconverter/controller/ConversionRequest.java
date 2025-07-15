package com.example.currencyconverter.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
 //  This is the public-facing part of our API. We will also create a simple DTO (Data Transfer Object) to represent the incoming request body.
// Why a DTO? This separates the API contract (what the user sends us) from our internal domain model (Conversion.java).
 //The user doesn't need to send an id or convertedAmount.
@Data
public class ConversionRequest {

    // @NotBlank / @Positive: These are validation annotations. Spring will automatically check incoming requests against these rules.
    @NotBlank(message = "Source currency cannot be blank")
    private String sourceCurrency;

    @NotBlank(message = "Target currency cannot be blank")
    private String targetCurrency;

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
}
