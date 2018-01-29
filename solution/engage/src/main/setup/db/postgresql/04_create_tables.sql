CREATE SCHEMA engage;

CREATE TABLE engage.configuration ( 
	id                   smallint  NOT NULL,
	auth_token_duration  smallint  NOT NULL,
	tax_percentage       numeric(19,2)  NOT NULL,
	CONSTRAINT pk_configuration PRIMARY KEY ( id )
 );

CREATE TABLE engage."user" ( 
	id                   integer  NOT NULL,
	gender               smallint  ,
	first_name           varchar(100)  ,
	last_name            varchar(100)  ,
	username             varchar(100)  NOT NULL,
	"password"           varchar(200)  NOT NULL,
	created_on           timestamp  NOT NULL,
	CONSTRAINT pk_user PRIMARY KEY ( id )
 );

CREATE TABLE engage."session" ( 
	id                   bigint DEFAULT nextval('session_seq'::regclass) NOT NULL,
	token                varchar(200)  NOT NULL,
	user_id              integer  NOT NULL,
	token_type           smallint  NOT NULL,
	create_time          timestamp  NOT NULL,
	access_time          timestamp  ,
	expiry_time          timestamp  ,
	ip_address           varchar(100)  ,
	active               bool  NOT NULL,
	CONSTRAINT pk_session PRIMARY KEY ( id ),
	CONSTRAINT udx_session_0 UNIQUE ( token ) ,
	CONSTRAINT udx_session_1 UNIQUE ( user_id, token_type ) ,
	CONSTRAINT pk_session_0 UNIQUE ( id, user_id ) 
 );

CREATE INDEX idx_session ON engage."session" ( user_id );

COMMENT ON CONSTRAINT udx_session_0 ON engage."session" IS 'partial index - where active';

COMMENT ON CONSTRAINT udx_session_1 ON engage."session" IS 'partial index - where active';

CREATE TABLE engage.expense ( 
	id                   bigint DEFAULT nextval('expense_seq'::regclass) NOT NULL,
	user_id              integer  NOT NULL,
	session_id           bigint  NOT NULL,
	amount               numeric(19,4)  NOT NULL,
	tax_value            numeric(19,4)  ,
	created_on           timestamp  NOT NULL,
	CONSTRAINT pk_expense PRIMARY KEY ( id )
 );

CREATE INDEX idx_expense ON engage.expense ( session_id, user_id );

CREATE INDEX idx_expense_0 ON engage.expense ( user_id );

ALTER TABLE engage.expense ADD CONSTRAINT fk_expense_session FOREIGN KEY ( session_id, user_id ) REFERENCES engage."session"( id, user_id );

ALTER TABLE engage.expense ADD CONSTRAINT fk_expense_user FOREIGN KEY ( user_id ) REFERENCES engage."user"( id );

ALTER TABLE engage."session" ADD CONSTRAINT fk_session_user FOREIGN KEY ( user_id ) REFERENCES engage."user"( id );

