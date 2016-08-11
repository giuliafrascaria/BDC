CREATE TABLE brightness
(
	ion character varying(64) NOT NULL,
	galaxy character varying(64) 
		REFERENCES galaxy(name),
	val float NOT NULL,
	flag boolean NOT NULL,
	
	CONSTRAINT pk_brightness PRIMARY KEY (ion, galaxy)
)