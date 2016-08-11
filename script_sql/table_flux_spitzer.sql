CREATE TABLE flux_spitzer
(
	ion character varying(64) NOT NULL,
	galaxy character varying(64) 
		REFERENCES galaxy(name),
	val float NOT NULL,
	error float,
	
	CONSTRAINT pk_flux_spitzer PRIMARY KEY (ion, galaxy)
)