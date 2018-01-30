package com.engagetech.engage.business;

import com.engagetech.engage.cache.EngageCache;
import com.engagetech.engage.commons.util.ExceptionUtil;
import com.engagetech.engage.commons.util.JDBIUtil;
import com.engagetech.engage.config.EngageConfiguration;
import com.engagetech.engage.dao.ExpenseDAO;
import com.engagetech.engage.entity.Expense;
import com.engagetech.engage.exception.ApplicationException;
import com.engagetech.engage.i18n.EngageLocale;
import com.engagetech.engage.i18n.EngageLocaleFactory;
import com.engagetech.engage.pico.ComponentManager;
import com.fasterxml.jackson.databind.JsonNode;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    
    public void updateExchangeRate() {
        EngageConfiguration configuration = ComponentManager.instance().getComponent(EngageConfiguration.class);
        EngageCache cache = ComponentManager.instance().getComponent(EngageCache.class);
        Client client = ComponentManager.instance().getComponent(Client.class);
        
        //we swithc base currency and exchange here, because we want rate for exchange currency
        
        Response response = client.target(configuration.getCurrencyConfiguration().getExchangeRateUrl())
                                  .queryParam("base", configuration.getCurrencyConfiguration().getExchangeCurrency())
                                  .queryParam("symbols", configuration.getCurrencyConfiguration().getBaseCurrency())
                                  .request(MediaType.APPLICATION_JSON)
                                  .get();
        
        if (response.getStatus() == 200) {
            final JsonNode jsonNode = response.readEntity(JsonNode.class);
            String rateString = jsonNode.get("rates").get(configuration.getCurrencyConfiguration().getBaseCurrency()).asText();
            
            BigDecimal rate = new BigDecimal(rateString);
            cache.put("exchangeRate", rate);
        } else {
            logger.error("Error while trying to load exchange rate. Status {} --- Response: {}", response.getStatus(), response);
        }

    }
    
}
