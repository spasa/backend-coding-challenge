package com.engagetech.engage.pico;

import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;
import org.skife.jdbi.v2.DBI;


public class ComponentManager {
    
    private static final MutablePicoContainer container = new PicoBuilder().
            withAnnotatedFieldInjection().
            withConstructorInjection().
            withAnnotatedMethodInjection().
            withAutomatic().
            withCaching().build();

    private ComponentManager() {
    }

    public static MutablePicoContainer instance() {
        return container;
    }

    public static Configuration getConfiguration() {
        return instance().getComponent(Configuration.class);
    }
    
    public static Environment getEnvironment() {
        return instance().getComponent(Environment.class);
    }
    
    public static DBI getDBI() {
        return instance().getComponent(DBI.class);
    }
}
