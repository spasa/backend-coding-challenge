CREATE OR REPLACE FUNCTION insert_expense(
    aUserId integer,
    aSessionId bigint,
    aForDate date,
    aAmount numeric(19, 4),
    aTaxValue numeric(19, 4),
    aReason varchar(500)
)RETURNS bigint AS
$BODY$
DECLARE
    qExpenseId bigint;
    qTaxPercentage numeric(19, 4);
BEGIN
    IF (aTaxValue IS NULL) THEN
        SELECT tax_percentage INTO qTaxPercentage FROM configuration ORDER BY id LIMIT 1;
        IF (NOT FOUND OR qTaxPercentage IS NULL) THEN
            RAISE 'NO_TAX_SETTING' USING ERRCODE = 'ET001'; 
        END IF;

        aTaxValue := aAmount - (aAmount / (1 + qTaxPercentage/100));
    END IF;

    INSERT INTO expense (user_id, session_id, for_date, amount, tax_value,  reason, created_on)
    VALUES (             aUserId, aSessionId, aForDate, aAmount, aTaxValue, aReason, current_timestamp) 
    RETURNING id INTO qExpenseId;

    RETURN qExpenseId;
END;
$BODY$
LANGUAGE plpgsql;