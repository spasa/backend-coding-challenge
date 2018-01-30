package com.engagetech.engage.resource;

import com.engagetech.engage.api.response.ErrorResponse;
import com.engagetech.engage.api.response.SuccessResponse;
import com.engagetech.engage.auth.Auth;
import com.engagetech.engage.business.ExpenseManager;
import com.engagetech.engage.commons.util.JDBIUtil;
import com.engagetech.engage.dao.ExpenseDAO;
import com.engagetech.engage.entity.Expense;
import com.engagetech.engage.entity.User;
import com.engagetech.engage.exception.ApplicationException;
import com.engagetech.engage.i18n.EngageLocale;
import com.engagetech.engage.i18n.EngageLocaleFactory;
import com.engagetech.engage.pico.ComponentManager;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.exceptions.DBIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExpenseResource {
    
    private static final Logger logger = LoggerFactory.getLogger(ExpenseResource.class);
    
    private final ExpenseManager expenseManager = ComponentManager.instance().getComponent(ExpenseManager.class);


    @POST
    public Response create(@Auth User user, 
                           Expense expense) {
        expense.setUserId(user.getId());
        try {
            expenseManager.createExpense(expense, user.getLanguage());
            return SuccessResponse.create(expense);
        } catch (ApplicationException ex) {
            logger.error("Error while creating expense", ex);
            return ErrorResponse.create(ex.getMessage());
        }
    }
    
    @GET
    public Response getExpenses(@Auth User user,
                                @QueryParam("offset") @DefaultValue("0") Integer offset,
                                @QueryParam("limit") @DefaultValue("500") Integer limit) {
        EngageLocale locale = ComponentManager.instance().getComponent(EngageLocaleFactory.class).getLocale(user.getLanguage());
        Handle handle = null;
        
        try {
            handle = JDBIUtil.beginTransaction();
            ExpenseDAO expenseDAO = handle.attach(ExpenseDAO.class);

            List<Expense> expenses = expenseDAO.find(user.getId(), offset, limit);
            
            handle.commit();
            
            return SuccessResponse.create(expenses);
        } catch (DBIException | SQLException ex) {
            logger.error("Error while loading expenses", ex);
            return ErrorResponse.create(locale.getMessage("expense.resource.get.expenses.error"));
        } finally {
            if (handle != null) {
                handle.rollback();
                handle.close();
            }
        }

    }
    
}
