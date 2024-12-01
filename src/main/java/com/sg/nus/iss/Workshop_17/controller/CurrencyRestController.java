package com.sg.nus.iss.Workshop_17.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sg.nus.iss.Workshop_17.model.Currency;
import com.sg.nus.iss.Workshop_17.service.CurrencyRestService;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyRestController {

    @Autowired
    CurrencyRestService currencyRestService;

    @GetMapping("")
    public List<Currency> getAllCurrency() {

        List<Currency> currencyList = new ArrayList<>();
        currencyList = currencyRestService.getAllCurrency();

        return currencyList;
    }

}
