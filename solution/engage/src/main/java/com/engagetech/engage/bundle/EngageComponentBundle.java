package com.engagetech.engage.bundle;

import com.engagetech.engage.business.ExpenseManager;
import com.engagetech.engage.config.EngageConfiguration;
import com.engagetech.engage.dao.common.GenderAsShortArgumentFactory;
import com.engagetech.engage.i18n.EngageENLocale;
import com.engagetech.engage.i18n.EngageLocaleFactory;
import com.engagetech.engage.i18n.EngageSRLocale;
import com.engagetech.engage.pico.ComponentManager;
import com.engagetech.engage.resource.ExpenseResource;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.io.IOException;
import org.picocontainer.MutablePicoContainer;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EngageComponentBundle<T extends EngageConfiguration> implements ConfiguredBundle<T> {
    
    private static final Logger logger = LoggerFactory.getLogger(EngageComponentBundle.class);

    public void run(T configuration, Environment environment) throws Exception {
        logger.info("RUN");
        MutablePicoContainer applicationScopeContainer = ComponentManager.instance();

        registerApplicationScope(applicationScopeContainer, configuration, environment);
        registerJdbiResources(applicationScopeContainer, environment, configuration);
        registerManagers(applicationScopeContainer, configuration, environment);
        registerResources(environment);
    }

    public void initialize(Bootstrap<?> btstrp) {
    }

    
    private void registerApplicationScope(MutablePicoContainer container, Configuration configuration, Environment environment) throws IOException {
        container.addComponent(Configuration.class, configuration);
        container.addComponent(Environment.class, environment);
        container.addComponent(EngageENLocale.class, new EngageENLocale());
        container.addComponent(EngageSRLocale.class, new EngageSRLocale());
        container.addComponent(EngageLocaleFactory.class, new EngageLocaleFactory());
    }

    private void registerJdbiResources(MutablePicoContainer container, Environment environment, EngageConfiguration cfg) throws ClassNotFoundException {
        final String dbVendor = cfg.getDatabaseFactory().getProperties().get("vendor");
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, cfg.getDatabaseFactory(), dbVendor);
        
        jdbi.registerArgumentFactory(new GenderAsShortArgumentFactory());

        container.addComponent(DBI.class, jdbi);
    }

    private void registerResources(Environment environment) {
        environment.jersey().register(ExpenseResource.class);
    }

    private void registerManagers(MutablePicoContainer container, T configuration, Environment environment) throws Exception {
        ExpenseManager expenseManager = new ExpenseManager();
        container.addComponent(ExpenseManager.class, expenseManager);
        
    }
    
    
}
