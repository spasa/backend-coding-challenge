package com.engagetech.engage.business;

import com.engagetech.engage.commons.util.JDBIUtil;
import com.engagetech.engage.dao.ExpenseDAO;
import com.engagetech.engage.entity.Expense;
import com.engagetech.engage.exception.ApplicationException;
import java.sql.SQLException;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.exceptions.DBIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExpenseManager {
    
    private static final Logger logger = LoggerFactory.getLogger(ExpenseManager.class);
    
    public void createExpense(Expense expense) throws ApplicationException {
//        TODO: add localization
        
        Handle handle = null;

        try {
            handle = JDBIUtil.beginTransaction();

            ExpenseDAO expenseDAO = handle.attach(ExpenseDAO.class);
            Long id = expenseDAO.insert(expense);
            
            expense.setId(id);
            
            handle.commit();
        } catch (DBIException | SQLException ex) {
//            TODO: handle exception

        } finally {
            if (handle != null) {
                handle.rollback();
                handle.close();
            }
        }
    }
    
}
