package com.engagetech.engage.exception;

import com.engagetech.engage.commons.util.ExceptionUtil;


public class ApplicationException extends Exception {
    
    private String throwableClass;
    private String stackTraceString;
    
    public ApplicationException(String message) {
        super(message);
    }
    
    public ApplicationException(String message, String throwableClass) {
        super(message);
        this.throwableClass = throwableClass;
    }
    
    public ApplicationException(Throwable t) {
        super(t);
    }
    
    public ApplicationException(String message, Throwable t) {
        super(message, t);
    }

    public String getThrowableClass() {
        if (throwableClass != null) {
            return throwableClass;
        } else {
            if (getStackTrace() != null) {
                for (StackTraceElement element : getStackTrace()) {
                    throwableClass = element.getClassName();
                    return throwableClass;
                }
            }
        }
        return null;
        
    }

    public String getStackTraceString() {
        if (stackTraceString != null) {
            return stackTraceString;
        } else {
            if (getStackTrace() != null) {
                ExceptionUtil.buildStackTrace(getStackTrace());
                return stackTraceString;
            }
        }
        return null;
    }
    
}
