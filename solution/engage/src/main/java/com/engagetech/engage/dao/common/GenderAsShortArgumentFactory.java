package com.engagetech.engage.dao.common;

import com.engagetech.engage.entity.Gender;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

public class GenderAsShortArgumentFactory implements ArgumentFactory<Gender> {

    @Override
    public boolean accepts(Class<?> type, Object value, StatementContext sc) {
        return (value != null) && Gender.class.isAssignableFrom(value.getClass());
    }

    @Override
    public Argument build(Class<?> type, final Gender t, StatementContext sc) {
        return new Argument() {
            @Override
            public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
                statement.setShort(position, t.getId());
            }
        };
    }
    
}
