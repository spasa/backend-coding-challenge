package com.engagetech.engage.api.auth;

import com.engagetech.engage.auth.Authenticator;
import com.engagetech.engage.auth.EngageCredentials;
import com.engagetech.engage.exception.ApplicationException;
import com.google.common.base.Optional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuthFactory<T> extends AbstractContainerRequestValueFactory<T> {

    private static final Logger logger = LoggerFactory.getLogger(AuthFactory.class);

    private final Authenticator<EngageCredentials, T> authenticator;

    public AuthFactory(Authenticator<EngageCredentials, T> authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public T provide() {
        try {
            // Get the Authorization header
            final String token = getContainerRequest().getHeaderString("X-ENGAGE-TOKEN");

//            if (StringUtils.isBlank(token)) {
//                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
//            }

            String language = null;
//            if (token != null) {
                final EngageCredentials credentials = new EngageCredentials(token, language);

                Optional<T> result = authenticator.authenticate(credentials);
                if (result.isPresent()) {
                    return result.get();
                }
                logger.warn(token + " sent from the client but it's expired");
//            }
        } catch (IllegalArgumentException e) {
            logger.debug("Error decoding credentials", e);
        } catch (ApplicationException e) {
            logger.warn("Error authenticating credentials", e);
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build());
        }

        // Must have failed to be here
        throw new WebApplicationException(Response.Status.UNAUTHORIZED);
    }

    @Override
    public void dispose(T instance) {
    }

}
