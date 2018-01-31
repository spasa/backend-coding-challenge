package com.engagetech.engage.test.integration;


import com.engagetech.engage.EngageApplication;
import com.engagetech.engage.config.EngageConfiguration;
import com.engagetech.engage.entity.Expense;
import com.engagetech.engage.pico.ComponentManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;


public class ExpenseResourceTest {
    
    private Client client;
    
    @ClassRule
    public static final DropwizardAppRule<EngageConfiguration> RULE =
            new DropwizardAppRule<>(EngageApplication.class, ResourceHelpers.resourceFilePath("engage.yml"));
    
    @Before
    public void setUp() throws Exception {
        client = ClientBuilder.newClient();
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }
    
    private Expense getExpense() {
        Expense expense = new Expense();
        
        expense.setForDate(new Date());
        expense.setAmount(BigDecimal.ONE);
        expense.setReason("Testing Reason");
        
        return expense;
    }
    
    @Test
    public void save() {
        Expense expense = getExpense();
        
        Response response = client.target("http://localhost:" + RULE.getLocalPort() + "/app/expenses")
                                  .request(MediaType.APPLICATION_JSON)
                                  .post(Entity.entity(expense, MediaType.APPLICATION_JSON_TYPE));
        
        Expense expenseResponse = response.readEntity(Expense.class);
        
        assertThat(response.getStatus(), is(200));
        assertNotNull(expenseResponse);
        assertNotNull(expenseResponse.getId());
        assertThat(expenseResponse.getId(), not(0));
        assertEquals(expense.getAmount().setScale(4), expenseResponse.getAmount().setScale(4));
        assertEquals(expense.getReason(), expenseResponse.getReason());
        
    }
    
    @Test
    public void read() throws IOException {
        Response response = client.target("http://localhost:" + RULE.getLocalPort() + "/app/expenses")
                                  .request(MediaType.APPLICATION_JSON)
                                  .get();
        
        List<Expense> expenses = new ObjectMapper().readValue(response.readEntity(String.class), new TypeReference<List<Expense>>() {});
        
        assertThat(response.getStatus(), is(200));
        assertNotNull(expenses);
    }
    
    @Test
    public void validationErrorOnSave() {
        Expense expense = new Expense();
        
        Response response = client.target("http://localhost:" + RULE.getLocalPort() + "/app/expenses")
                                  .request(MediaType.APPLICATION_JSON)
                                  .post(Entity.entity(expense, MediaType.APPLICATION_JSON_TYPE));
        
        String responseMessage = response.readEntity(String.class);
        
        assertThat(response.getStatus(), is(200));
        assertNotNull(responseMessage);
        Assert.assertTrue(responseMessage.contains("can not be empty"));
    }
    
    @Test
    public void saveWithExchangeRate() {
        EngageConfiguration config = ComponentManager.instance().getComponent(EngageConfiguration.class);
        
        Expense expense = getExpense();
        expense.setCurrency(config.getCurrencyConfiguration().getExchangeCurrency());
        
        Response response = client.target("http://localhost:" + RULE.getLocalPort() + "/app/expenses")
                                  .request(MediaType.APPLICATION_JSON)
                                  .post(Entity.entity(expense, MediaType.APPLICATION_JSON_TYPE));
        
        Expense expenseResponse = response.readEntity(Expense.class);
        
        assertThat(response.getStatus(), is(200));
        assertNotNull(expenseResponse);
        assertNotNull(expenseResponse.getId());
        assertThat(expenseResponse.getId(), not(0));
        assertThat(expense.getAmount().setScale(4), not(expenseResponse.getAmount().setScale(4)));
        assertEquals(expense.getReason(), expenseResponse.getReason());
        
    }
    
}
