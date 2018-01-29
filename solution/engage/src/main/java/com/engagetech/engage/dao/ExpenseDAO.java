package com.engagetech.engage.dao;

import com.engagetech.engage.entity.Expense;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;


public interface ExpenseDAO {
    
    @SqlQuery("SELECT * FROM insert_expense(:userId, :sessionId, :amount, :taxValue)")
    public abstract Long insert(@BindBean Expense expense);
    
}
