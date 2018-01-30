package com.engagetech.engage.bundle;

import com.engagetech.engage.api.auth.AuthFactoryProvider;
import com.engagetech.engage.api.auth.AuthInjectResolver;
import com.engagetech.engage.auth.Auth;
import com.engagetech.engage.config.EngageConfiguration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import javax.inject.Singleton;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;


public class EngageAuthenticationBundle implements ConfiguredBundle<EngageConfiguration> {

    @Override
    public void run(EngageConfiguration configuration, Environment e) throws Exception {
        e.jersey().register(new AbstractBinder() {

            @Override
            protected void configure() {
                bind(AuthFactoryProvider.class).to(ValueFactoryProvider.class).in(Singleton.class);
                bind(AuthInjectResolver.class).to(new TypeLiteral<InjectionResolver<Auth>>() {}).in(Singleton.class);
            }
        });
    }

    @Override
    public void initialize(Bootstrap<?> btstrp) {
    }
    
}
