package com.example.currencyconverter.client;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.math.BigDecimal;

@Component // Makes this class a Spring bean.
public class CurrencyApiClient {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String apiKey;
    private final ObjectMapper objectMapper; // to parse JSON


    public CurrencyApiClient(RestTemplate restTemplate,
                             @Value("${freecurrency.api.url}") String apiUrl,
                             @Value("${freecurrency.api.key}") String apiKey)  {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.objectMapper = new ObjectMapper();
    }

    public BigDecimal getExchangeRate(String sourceCurrency, String targetCurrency){
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl) //UriComponentsBuilder: Safely builds the request URL with parameters.
                .queryParam("apikey", apiKey)
                .queryParam("base_currency", sourceCurrency)
                .queryParam("currencies", targetCurrency)
                .toUriString();

        String jsonResponse = restTemplate.getForObject(url, String.class); // restTemplate.getForObject(): Makes the actual HTTP GET call.

        try {
            JsonNode root = objectMapper.readTree(jsonResponse); //ObjectMapper / JsonNode: The API returns JSON
            JsonNode dataNode = root.path("data");
            if (dataNode.has(targetCurrency)) {
                return new BigDecimal(dataNode.get(targetCurrency).asText());
            } else {
                throw new RuntimeException("Target currency not found in API response. ");
            }
        } catch (IOException e) {
            throw new RuntimeException("failed to parse API response", e);
        }
    }
}
