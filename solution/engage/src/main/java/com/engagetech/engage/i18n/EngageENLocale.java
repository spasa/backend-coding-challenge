package com.engagetech.engage.i18n;

import java.util.Locale;
import java.util.ResourceBundle;


public class EngageENLocale extends EngageLocale {

    public EngageENLocale() {
        initialize();
    }

    @Override
    protected void initialize() {
        Locale locale = new Locale("en");
        messages = ResourceBundle.getBundle("Messages", locale);
    }
    
}
