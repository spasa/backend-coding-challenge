package com.engagetech.engage.dao;

import com.engagetech.engage.dao.mapper.ExpenseMapper;
import com.engagetech.engage.entity.Expense;
import com.google.common.collect.ImmutableList;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Define;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

@UseStringTemplate3StatementLocator
public interface ExpenseDAO {
    
    @SqlQuery("SELECT * FROM insert_expense(:userId, :sessionId, :amount, :taxValue)")
    public abstract Long insert(@BindBean Expense expense);
    
    @SqlQuery()
    @Mapper(ExpenseMapper.class)
    public abstract ImmutableList<Expense> find(@Bind("userId") Integer userId,
                                                @Bind("offset") @Define("OFFSET") Integer offset,
                                                @Bind("limit") @Define("LIMIT") Integer limit);
    
}
