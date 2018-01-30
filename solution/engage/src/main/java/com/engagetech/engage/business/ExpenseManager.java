package com.engagetech.engage.business;

import com.engagetech.engage.commons.util.ExceptionUtil;
import com.engagetech.engage.commons.util.JDBIUtil;
import com.engagetech.engage.dao.ExpenseDAO;
import com.engagetech.engage.entity.Expense;
import com.engagetech.engage.exception.ApplicationException;
import com.engagetech.engage.i18n.EngageLocale;
import com.engagetech.engage.i18n.EngageLocaleFactory;
import com.engagetech.engage.pico.ComponentManager;
import java.sql.SQLException;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.exceptions.DBIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExpenseManager {
    
    private static final Logger logger = LoggerFactory.getLogger(ExpenseManager.class);
    
    public void createExpense(Expense expense, String language) throws ApplicationException {
        EngageLocale locale = ComponentManager.instance().getComponent(EngageLocaleFactory.class).getLocale(language);
        Handle handle = null;

        try {
            handle = JDBIUtil.beginTransaction();

            ExpenseDAO expenseDAO = handle.attach(ExpenseDAO.class);
            Long id = expenseDAO.insert(expense);
            
            expense.setId(id);
            
            handle.commit();
        } catch (DBIException | SQLException ex) {
            ExceptionUtil.handleException(ex, locale, "expense.manager.create.expense.error");
        } finally {
            if (handle != null) {
                handle.rollback();
                handle.close();
            }
        }
    }
    
}
