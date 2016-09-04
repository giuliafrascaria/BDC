CREATE TABLE galaxy
(
	name character varying(64) NOT NULL,
	spectralClass character varying(64) NOT NULL,
	IRSmode character varying(64),
	distance real,
	metalErr real,
	metalVal real,
	
	CONSTRAINT pk_galaxy PRIMARY KEY (name)
)