package com.sg.nus.iss.Workshop_17.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sg.nus.iss.Workshop_17.model.Currency;
import com.sg.nus.iss.Workshop_17.service.CurrencyRestService;

@Controller
@RequestMapping("/")
public class CurrencyController {

    @Autowired
    CurrencyRestService currencyRestService;

    @GetMapping("")
    public String showCurrencyConverter(Model model) {

        List<Currency> currencyList = new ArrayList<>();
        currencyList = currencyRestService.getAllCurrency();

        model.addAttribute("currencies", currencyList);

        return "index";
    }

    @PostMapping("/converted")
    public String convertCurrency(
            @RequestParam("fromCurrency") String fromCurrencyId,
            @RequestParam("toCurrency") String toCurrencyId,
            @RequestParam("amount") double amount,
            Model model) {

        double conversionRate = currencyRestService.getCurrencyRate(fromCurrencyId, toCurrencyId);
        // Calculate the converted amount
        double convertedAmount = conversionRate * amount;

        // Add data to the model
        model.addAttribute("fromCurrency", fromCurrencyId);
        model.addAttribute("toCurrency", toCurrencyId);
        model.addAttribute("amount", amount);
        model.addAttribute("conversionRate", conversionRate);
        model.addAttribute("convertedAmount", convertedAmount);

        // Return the view name
        return "converted";
    }

}
