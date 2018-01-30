package com.engagetech.engage.i18n;

import java.util.Locale;
import java.util.ResourceBundle;


public class EngageSRLocale extends EngageLocale {

    public EngageSRLocale() {
        initialize();
    }

    @Override
    protected void initialize() {
        Locale locale = new Locale("sr");
        messages = ResourceBundle.getBundle("Messages", locale);
    }
    
}
