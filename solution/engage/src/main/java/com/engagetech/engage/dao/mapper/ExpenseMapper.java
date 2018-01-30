package com.engagetech.engage.dao.mapper;

import com.engagetech.engage.entity.Expense;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class ExpenseMapper implements ResultSetMapper<Expense> {

    public Expense map(int i, ResultSet rs, StatementContext sc) throws SQLException {
        Expense expense = new Expense();
        
        expense.setId(rs.getLong("id"));
        expense.setUserId(rs.getInt("user_id"));
        expense.setSessionId(rs.getLong("session_id"));
        expense.setAmount(rs.getBigDecimal("amount"));
        expense.setTaxValue(rs.getBigDecimal("tax_value"));
        
        return expense;
    }
    
}
