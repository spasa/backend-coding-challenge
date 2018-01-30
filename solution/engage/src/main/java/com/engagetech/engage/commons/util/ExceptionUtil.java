package com.engagetech.engage.commons.util;

import com.engagetech.engage.commons.constant.ApplicationConstants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.slf4j.LoggerFactory;


public class ExceptionUtil {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);
    
    public static StringBuilder buildStackTrace(StackTraceElement[] steArr) {
        StringBuilder builder = new StringBuilder();
        for (StackTraceElement element : steArr) {
            builder.append(element.toString());
            builder.append(ApplicationConstants.NEW_LINE);
        }
        return builder;
    }
    
    public static String getAsString(Throwable t) {
        ByteArrayOutputStream os = null;
        PrintStream ps = null;
        try {
            os = new ByteArrayOutputStream();
            ps = new PrintStream(os);
            t.printStackTrace(ps);
            return os.toString();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ex) {
                    logger.error("Couldn't close ByteArrayOutputStream", ex);
                }
            }
        }
        
    }
    
    
}
