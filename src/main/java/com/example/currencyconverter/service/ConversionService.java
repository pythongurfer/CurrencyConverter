// src/main/java/com/example/currencyconverter/service/ConversionService.java
package com.example.currencyconverter.service;

import com.example.currencyconverter.client.CurrencyApiClient;
import com.example.currencyconverter.model.Conversion;
import com.example.currencyconverter.repository.ConversionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service //@Service: Marks this as the service layer bean.
public class ConversionService {

    private final ConversionRepository conversionRepository;
    private final CurrencyApiClient currencyApiClient;

    public ConversionService(ConversionRepository conversionRepository, CurrencyApiClient currencyApiClient) {
        this.conversionRepository = conversionRepository;
        this.currencyApiClient = currencyApiClient;
    }

    public Conversion createConversion(String sourceCurrency, String targetCurrency, BigDecimal amount) {
        // 1. Get the exchange rate from the external API
        BigDecimal exchangeRate = currencyApiClient.getExchangeRate(sourceCurrency, targetCurrency);

        // 2. Calculate the converted amount
        BigDecimal convertedAmount = amount.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);

        // 3. Create and save the conversion object
        Conversion conversion = new Conversion();
        conversion.setSourceCurrency(sourceCurrency);
        conversion.setTargetCurrency(targetCurrency);
        conversion.setAmount(amount);
        conversion.setExchangeRate(exchangeRate);
        conversion.setConvertedAmount(convertedAmount);

        return conversionRepository.save(conversion);
    }

    public List<Conversion> getAllConversions() {
        return conversionRepository.findAll();
    }

    public Conversion getConversionById(String id) {
        return conversionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversion not found with id: " + id));
    }

    public void deleteConversionById(String id) {
        if (!conversionRepository.existsById(id)) {
            throw new RuntimeException("Conversion not found with id: " + id);
        }
        conversionRepository.deleteById(id);
    }
}