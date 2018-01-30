package com.engagetech.engage.auth;

import com.engagetech.engage.exception.ApplicationException;
import com.google.common.base.Optional;


public interface Authenticator<C extends Object, P extends Object> {

    public Optional<P> authenticate(C c) throws ApplicationException;
}
