INSERT INTO configuration(id, auth_token_duration, tax_percentage)
VALUES(                   1, 15,                    20);

INSERT INTO "user"(id, username, password, created_on) --do not need real password (hashed), authentication not implemented
VALUES(            1, 'engage', 'engage',  current_timestamp);

INSERT INTO "session"(id, token, user_id, token_type, create_time,       expiry_time,                           active) --expiry time not actually important in current implementation
VALUES(                1, 'aEnGage', 1,     1,        current_timestamp, current_timestamp + interval '7 days', true);