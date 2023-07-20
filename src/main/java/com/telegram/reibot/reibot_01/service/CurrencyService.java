package com.telegram.reibot.reibot_01.service;

import com.telegram.reibot.reibot_01.model.CurrencyModel;
import com.telegram.reibot.reibot_01.model.CurrencyRateModel;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class CurrencyService {

    public static String getCurrencyRate(String message, CurrencyModel model) throws IOException, ParseException {
        URL url = new URL("https://www.cbr-xml-daily.ru/latest.js");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";
        while (scanner.hasNext()){
            result += scanner.nextLine();
        }
        JSONObject object = new JSONObject(result);

        model.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(object.getString("date")));
        //model.setDate(new Date(object.getInt("timestamp")));
        model.setCur_Base(object.getString("base"));
        var rates = object.getJSONObject("rates");
        ArrayList<CurrencyRateModel> currencyRates = new ArrayList<>();
        if(message.toUpperCase().equals("ALL")){
            rates.keySet().forEach(key -> {
                currencyRates.add(new CurrencyRateModel(key, rates.getDouble(key)));
            });
        }else {
            rates.keySet().forEach(key -> {
                if(key.equals(message.toUpperCase()))
                    currencyRates.add(new CurrencyRateModel(key, rates.getDouble(key)));
            });
        }
        model.setRates(currencyRates);

        return "Official rate of " + model.getCur_Base() + "\n" +
                "on the date: " + getFormatDate(model) + "\n" +
                "is: " + model.getAllRates();

    }

    private static String getFormatDate(CurrencyModel model) {
        return new SimpleDateFormat("dd MMM yyyy").format(model.getDate());
    }
}