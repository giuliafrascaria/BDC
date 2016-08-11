CREATE TABLE alternative_name
(
	name character varying(64) NOT NULL,
	galaxy character varying(64) 
		REFERENCES galaxy(name),
		
	CONSTRAINT pk_alternative_name PRIMARY KEY (galaxy)
)