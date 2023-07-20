package com.telegram.reibot.reibot_01.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class CurrencyModel {
    Date date;
    String cur_Base;
    ArrayList<CurrencyRateModel> rates;

    public String getAllRates(){
        StringBuffer result  = new StringBuffer();
        result.append("\n----\n");
        rates.forEach(rate -> result.append(cur_Base).append(" - ").append(rate.curAbbreviation).append(": ").append(rate.curRate).append("\n")
                .append(rate.curAbbreviation).append(" - ").append(cur_Base).append(": ").append(1.0/rate.curRate).append("\n----\n"));
        return result.toString();
    }
}
