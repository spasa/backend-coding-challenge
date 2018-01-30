package com.engagetech.engage.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public class EngageConfiguration extends Configuration {
    
    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();
    
    @Valid
    @NotNull
    @JsonProperty
    private JerseyClientConfiguration jerseyClientConfiguration = new JerseyClientConfiguration();
    
    @JsonProperty
    @Valid
    private CronJobConfiguration cronJob;
    
    @JsonProperty
    @Valid
    private CurrencyConfiguration currencyConfiguration;

    public DataSourceFactory getDatabaseFactory() {
        return database;
    }

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClientConfiguration;
    }

    public CronJobConfiguration getCronJob() {
        return cronJob;
    }

    public CurrencyConfiguration getCurrencyConfiguration() {
        return currencyConfiguration;
    }
    
}
