package com.engagetech.engage.api.auth;

import com.engagetech.engage.auth.Auth;
import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;


public class AuthInjectResolver extends ParamInjectionResolver<Auth>  {

    public AuthInjectResolver() {
        super(AuthFactoryProvider.class);
    }
    
    
}
