package com.engagetech.engage.auth;

import com.engagetech.engage.entity.User;
import com.engagetech.engage.exception.ApplicationException;
import com.google.common.base.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EngageAuthenticator implements Authenticator<EngageCredentials, User> {
    
    private static final Logger logger = LoggerFactory.getLogger(EngageAuthenticator.class);
    
    @Override
    public Optional<User> authenticate(EngageCredentials credentials) throws ApplicationException {
        Optional<User> user = getUserByToken(credentials);
        if (!user.isPresent()) {
            return Optional.absent();
        }
        
        return user;
    }
    
    private Optional<User> getUserByToken(EngageCredentials credentials) {
// We would read user from session table here.
//For sake of this task, we are just mocking (hardcoded) user and return it

        User user = new User();
        user.setId(1);
        user.setSessionId(1l);

        return Optional.of(user);
    }

}
