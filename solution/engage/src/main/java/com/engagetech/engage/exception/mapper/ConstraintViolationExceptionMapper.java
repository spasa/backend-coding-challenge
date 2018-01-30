package com.engagetech.engage.exception.mapper;

import com.engagetech.engage.api.response.ErrorResponse;
import com.engagetech.engage.i18n.EngageLocale;
import com.engagetech.engage.i18n.EngageLocaleFactory;
import com.engagetech.engage.pico.ComponentManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;


public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    
    @Override
    public Response toResponse(ConstraintViolationException runtime) {
        StringBuilder sb = new StringBuilder();
        EngageLocale locale = getLocale();
        
        sb.append("{ ");
        
        for (ConstraintViolation<?> constraintViolation : runtime.getConstraintViolations()) {
            sb.append(locale.getMessage(constraintViolation.getMessage()));
            sb.append(", ");
        }
        
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("}");

        return ErrorResponse.create(sb.toString());

    }
    
    private EngageLocale getLocale() {
        String language = null; //we would use context and get language from header or cookie
        return ComponentManager.instance().getComponent(EngageLocaleFactory.class).getLocale(language);
        
    }

}
