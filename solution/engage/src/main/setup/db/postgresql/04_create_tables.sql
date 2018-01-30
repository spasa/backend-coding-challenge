CREATE TABLE configuration ( 
	id                   smallint  NOT NULL,
	auth_token_duration  smallint  NOT NULL,
	tax_percentage       numeric(19,2)  NOT NULL,
	CONSTRAINT pk_configuration PRIMARY KEY ( id )
 );

ALTER TABLE configuration ADD CONSTRAINT ck_0 CHECK ( tax_percentage > 0 );

CREATE TABLE "user" ( 
	id                   integer  NOT NULL,
	gender               smallint  ,
	first_name           varchar(100)  ,
	last_name            varchar(100)  ,
	username             varchar(100)  NOT NULL,
	"password"           varchar(200)  NOT NULL,
	created_on           timestamp  NOT NULL,
	CONSTRAINT pk_user PRIMARY KEY ( id )
 );

CREATE TABLE "session" ( 
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

CREATE INDEX idx_session ON "session" ( user_id );

COMMENT ON CONSTRAINT udx_session_0 ON "session" IS 'partial index - where active';

COMMENT ON CONSTRAINT udx_session_1 ON "session" IS 'partial index - where active';

CREATE TABLE expense ( 
	id                   bigint DEFAULT nextval('expense_seq'::regclass) NOT NULL,
	user_id              integer  NOT NULL,
	session_id           bigint  NOT NULL,
	for_date             date  NOT NULL,
	amount               numeric(19,4)  NOT NULL,
	tax_value            numeric(19,4)  ,
	reason               varchar(500)  NOT NULL,
	created_on           timestamp  NOT NULL,
	CONSTRAINT pk_expense PRIMARY KEY ( id )
 );

ALTER TABLE expense ADD CONSTRAINT ck_1 CHECK ( amount > 0 );

ALTER TABLE expense ADD CONSTRAINT ck_2 CHECK ( tax_value > 0 );

CREATE INDEX idx_expense ON expense ( session_id, user_id );

CREATE INDEX idx_expense_0 ON expense ( user_id );

ALTER TABLE expense ADD CONSTRAINT fk_expense_session FOREIGN KEY ( session_id, user_id ) REFERENCES "session"( id, user_id );

ALTER TABLE expense ADD CONSTRAINT fk_expense_user FOREIGN KEY ( user_id ) REFERENCES "user"( id );

ALTER TABLE "session" ADD CONSTRAINT fk_session_user FOREIGN KEY ( user_id ) REFERENCES "user"( id );

