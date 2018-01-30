package com.engagetech.engage.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import javax.validation.constraints.NotNull;


public class CurrencyConfiguration extends Configuration {
    
    @NotNull
    @JsonProperty
    private String exchangeRateUrl;
    
    @NotNull
    @JsonProperty
    private String baseCurrency;
    
    @NotNull
    @JsonProperty
    private String exchangeCurrency;

    public String getExchangeRateUrl() {
        return exchangeRateUrl;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getExchangeCurrency() {
        return exchangeCurrency;
    }
    
}
