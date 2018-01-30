package com.engagetech.engage.i18n;

import java.util.ResourceBundle;


public abstract class EngageLocale {
    
    protected ResourceBundle messages;
    
    public String getMessage(String key) {
        return messages.getString(key);
    }
    
    protected abstract void initialize();
    
}
