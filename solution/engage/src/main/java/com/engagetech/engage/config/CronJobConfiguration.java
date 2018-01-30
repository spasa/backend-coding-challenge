package com.engagetech.engage.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import javax.validation.constraints.NotNull;


public class CronJobConfiguration extends Configuration {
    
    @NotNull
    @JsonProperty
    private String updateExchangeRateJobCronExpression;

    public String getUpdateExchangeRateJobCronExpression() {
        return updateExchangeRateJobCronExpression;
    }
    
    
}
