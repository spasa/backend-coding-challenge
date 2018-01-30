package com.engagetech.engage.api.auth;

import com.engagetech.engage.auth.Auth;
import com.engagetech.engage.auth.Authenticator;
import com.engagetech.engage.auth.EngageAuthenticator;
import com.engagetech.engage.auth.EngageCredentials;
import com.engagetech.engage.entity.User;
import javax.inject.Inject;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.model.Parameter;


public class AuthFactoryProvider extends AbstractValueFactoryProvider {

    private final Authenticator<EngageCredentials, User> authenticator = new EngageAuthenticator();

    @Inject
    public AuthFactoryProvider(MultivaluedParameterExtractorProvider mpep, ServiceLocator locator) {
        super(mpep, locator, Parameter.Source.UNKNOWN);
    }

    @Override
    public PriorityType getPriority() {
        return Priority.NORMAL;
    }

    @Override
    protected Factory<?> createValueFactory(Parameter parameter) {
        Class<?> paramType = parameter.getRawType();
        Auth annotation = parameter.getAnnotation(Auth.class);
        if (annotation != null && paramType.isAssignableFrom(User.class)) {
            return new AuthFactory(authenticator);
        }
        return null;
    }

}
