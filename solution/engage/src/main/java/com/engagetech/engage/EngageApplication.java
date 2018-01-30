package com.engagetech.engage;

import com.engagetech.engage.bundle.EngageAuthenticationBundle;
import com.engagetech.engage.bundle.EngageComponentBundle;
import com.engagetech.engage.config.EngageConfiguration;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EngageApplication extends Application<EngageConfiguration> {
    
    private static final Logger logger = LoggerFactory.getLogger(EngageApplication.class);
    
    public static void main(String[] args) throws Exception {
        new EngageApplication().run(args);
    }
    
    @Override
    public void initialize(Bootstrap<EngageConfiguration> bootstrap) {
        logger.info("Initialize");
        bootstrap.addBundle(new EngageComponentBundle());
        bootstrap.addBundle(new EngageAuthenticationBundle());
        bootstrap.addBundle(new AssetsBundle("/www/engage/assets/", "/", "index.html"));
    }

    @Override
    public void run(EngageConfiguration t, Environment environment) throws Exception {
        environment.jersey().setUrlPattern("/app/*");
        
        logger.info("ENGAGE STARTED");
    }
    
}
