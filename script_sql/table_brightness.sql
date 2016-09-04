CREATE TABLE brightness
(
	ion character varying(64) NOT NULL,
	galaxy character varying(64) NOT NULL
		REFERENCES galaxy(name),
	val real NOT NULL,
	flag boolean NOT NULL,
	
	CONSTRAINT pk_brightness PRIMARY KEY (ion, galaxy)
)