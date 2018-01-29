CREATE OR REPLACE FUNCTION insert_expense(
    aUserId integer,
    aSessionId bigint,
    aAmount numeric(19, 4),
    aTaxValue numeric(19, 4)
)RETURNS bigint AS
$BODY$
DECLARE
    qExpenseId bigint;
BEGIN

    INSERT INTO expense (user_id, session_id, amount, tax_value, created_on)
    VALUES (             aUserId, aSessionId, aAmount, aTaxValue, current_timestamp) 
    RETURNING id INTO qExpenseId;

    RETURN qExpenseId;
END;
$BODY$
LANGUAGE plpgsql;