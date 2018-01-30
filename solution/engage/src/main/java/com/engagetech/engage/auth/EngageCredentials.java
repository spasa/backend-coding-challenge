package com.engagetech.engage.auth;

import java.util.Objects;


public class EngageCredentials {
    
    private final String token;

    private final String language;
    
        /**
     * @param token The session ID acting as a surrogate for the OpenID
     * token
     * @param language
     */
    public EngageCredentials(String token, String language) {
        this.token = token;
        this.language = language;
    }

    /**
     * @return The OpenID token
     */
    public String getToken() {
        return token;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.token);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EngageCredentials other = (EngageCredentials) obj;
        if (!Objects.equals(this.token, other.token)) {
            return false;
        }
        return true;
    }

}
