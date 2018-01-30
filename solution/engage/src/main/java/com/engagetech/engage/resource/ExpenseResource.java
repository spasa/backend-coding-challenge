package com.engagetech.engage.resource;

import com.engagetech.engage.api.response.ErrorResponse;
import com.engagetech.engage.api.response.SuccessResponse;
import com.engagetech.engage.business.ExpenseManager;
import com.engagetech.engage.entity.Expense;
import com.engagetech.engage.exception.ApplicationException;
import com.engagetech.engage.pico.ComponentManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    
}
