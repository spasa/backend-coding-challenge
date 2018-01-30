package com.engagetech.engage.i18n;

import com.engagetech.engage.pico.ComponentManager;
import org.apache.commons.lang3.StringUtils;


public class EngageLocaleFactory {
    
    public EngageLocale getLocale(String language) {
        if (StringUtils.isBlank(language)) {
            return ComponentManager.instance().getComponent(EngageENLocale.class);
        }
        
        if ("sr".equalsIgnoreCase(language)) {
            return ComponentManager.instance().getComponent(EngageSRLocale.class);
        } else if ("en".equalsIgnoreCase(language)) {   
            return ComponentManager.instance().getComponent(EngageENLocale.class);
        } else {
            return ComponentManager.instance().getComponent(EngageENLocale.class);
        }
    }
    
}
