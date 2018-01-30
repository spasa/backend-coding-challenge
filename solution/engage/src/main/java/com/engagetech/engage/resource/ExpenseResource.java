package com.engagetech.engage.resource;

import com.engagetech.engage.api.response.ErrorResponse;
import com.engagetech.engage.api.response.SuccessResponse;
import com.engagetech.engage.business.ExpenseManager;
import com.engagetech.engage.commons.util.JDBIUtil;
import com.engagetech.engage.dao.ExpenseDAO;
import com.engagetech.engage.entity.Expense;
import com.engagetech.engage.exception.ApplicationException;
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

@Path("app/expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExpenseResource {
    
    private static final Logger logger = LoggerFactory.getLogger(ExpenseResource.class);
    
    private final ExpenseManager expenseManager = ComponentManager.instance().getComponent(ExpenseManager.class);


    @POST
    public Response create(Expense expense) {
//        TODO: add authorization
        try {
            expenseManager.createExpense(expense);
            return SuccessResponse.create(expense);
        } catch (ApplicationException ex) {
            logger.error("Error while creating expense", ex);
            return ErrorResponse.create(ex.getMessage());
        }
    }
    
    @GET
    public Response getExpenses(@QueryParam("offset") @DefaultValue("0") Integer offset,
                                @QueryParam("limit") @DefaultValue("500") Integer limit) {
        Handle handle = null;
        
        try {
            handle = JDBIUtil.beginTransaction();
            ExpenseDAO expenseDAO = handle.attach(ExpenseDAO.class);

            List<Expense> expenses = expenseDAO.find(1, offset, limit);
            
            handle.commit();
            
            return SuccessResponse.create(expenses);
        } catch (DBIException | SQLException ex) {
            logger.error("Error while loading verification docs", ex);
            return ErrorResponse.create("Error Message");
        } finally {
            if (handle != null) {
                handle.rollback();
                handle.close();
            }
        }

    }
    
}
