package com.sg.nus.iss.Workshop_17.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sg.nus.iss.Workshop_17.constant.Url;
import com.sg.nus.iss.Workshop_17.model.Currency;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Service
public class CurrencyRestService {

    RestTemplate restTemplate = new RestTemplate();

    public List<Currency> getAllCurrency() {

        String currencyData = restTemplate.getForObject(Url.currencyUrl, String.class);
        JsonReader jReader = Json.createReader(new StringReader(currencyData));
        JsonObject jObject = jReader.readObject();

        JsonObject jDataObject = jObject.getJsonObject("results");

        List<Currency> getAllCurrency = new ArrayList<>();

        Set<Entry<String, JsonValue>> entries = jDataObject.entrySet();

        for (Entry<String, JsonValue> entry : entries) {
            Currency c = new Currency();
            c.setCurrencyName(entry.getValue().asJsonObject().getString("currencyName"));
            c.setCurrencySymbol(entry.getValue().asJsonObject().getString("currencySymbol"));
            c.setCurrencyId(entry.getValue().asJsonObject().getString("currencyId"));
            getAllCurrency.add(c);
        }
        getAllCurrency.sort(Comparator.comparing(Currency::getCurrencyName));

        return getAllCurrency;
    }

    public double getCurrencyRate(String fromCurrency, String toCurrency) {
        // Build the dynamic query string
        String query = fromCurrency + "_" + toCurrency;

        // Construct the complete API URL
        String url = String.format("%s?q=%s&compact=ultra&apiKey=%s", Url.apiUrl, query, Url.apiKey);

        String conversionRateData = restTemplate.getForObject(url, String.class);

        JsonReader jReader = Json.createReader(new StringReader(conversionRateData));
        JsonObject jObject = jReader.readObject();

        // Get the conversion rate from the API response
        Double conversionRate = jObject.getJsonNumber(query).doubleValue();

        return conversionRate;
    }

}
