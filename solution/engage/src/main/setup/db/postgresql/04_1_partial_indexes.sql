ALTER TABLE session DROP CONSTRAINT udx_session_0;
CREATE UNIQUE INDEX udx_session_0 ON session (token) WHERE active;

ALTER TABLE session DROP CONSTRAINT udx_session_1;
CREATE UNIQUE INDEX udx_session_1 ON session (user_id, token_type) WHERE active;