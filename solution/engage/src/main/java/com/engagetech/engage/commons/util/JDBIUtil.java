package com.engagetech.engage.commons.util;

import com.engagetech.engage.pico.ComponentManager;
import java.sql.SQLException;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;


public class JDBIUtil {
    
    public static Handle getHandle() throws SQLException {
        DBI dbi = ComponentManager.getDBI();
        Handle handle = dbi.open();
        handle.getConnection().setAutoCommit(false);
        
        return handle;
    }
    
//    This rollback before begin maybe looks weird, but it actually makes sense.
//    We can talk about it on next round of interview
    public static Handle beginTransaction() throws SQLException {
        DBI dbi = ComponentManager.getDBI();
        Handle handle = dbi.open();
        handle.getConnection().setAutoCommit(false);
        
        handle.rollback();
        handle.begin();
        
        return handle;
    }
    
}
