CREATE TABLE users
(  
	userID character varying(64) NOT NULL,
	password character varying(64) NOT NULL,
	mail character varying(64) NOT NULL,
	firstname character varying(64) NOT NULL,
	lastname character varying(64) NOT NULL,
	accountType smallint NOT NULL,
	
	CONSTRAINT pk_users PRIMARY KEY (userID)
)