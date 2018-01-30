package com.engagetech.engage.commons.util;

import com.engagetech.engage.commons.constant.ApplicationConstants;
import com.engagetech.engage.exception.ApplicationException;
import com.engagetech.engage.i18n.EngageLocale;
import com.ibm.icu.text.MessageFormat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.postgresql.util.PSQLException;
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
    
    public static void handleException(Exception ex, 
                                       EngageLocale locale,
                                       String generalErrorKey) throws ApplicationException {
        
        if (ex instanceof ApplicationException) {
            throw new ApplicationException(ex.getMessage(), ex);
        } else if (ex.getCause() != null && ex.getCause() instanceof PSQLException) {
            if (ApplicationConstants.PSQL_ERROR_CODE.equals(((PSQLException) ex.getCause()).getSQLState())) {
                Map<String, String> queryParameters = new HashMap<>();
                String messageKey = handleQueryParameters(ex, queryParameters);
                
                throw new ApplicationException(MessageFormat.format(locale.getMessage("PSQLException." + messageKey),
                                                                    queryParameters));
            
            }
        }
        throw new ApplicationException(locale.getMessage(generalErrorKey), ex);
    }
    
    public static String handleQueryParameters(Exception ex, Map<String, String> queryParameters) {
        if (queryParameters == null) {
            queryParameters = new HashMap<>();
        }
        
        String[] query = ((PSQLException) ex.getCause()).getServerErrorMessage().getMessage().split("\\?");
        
        if (query.length > 1) {
            String[] queryParams = query[1].split("&");
            
            for (String queryParam : queryParams) {
                int idx = queryParam.indexOf("=");
                queryParameters.put(queryParam.substring(0, idx), queryParam.substring(idx + 1));
            }
        }
        
        return query[0];
    }
    
    
}
