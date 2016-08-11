CREATE TABLE flux_spitzer
(
	ion character varying(64) NOT NULL,
	galaxy character varying(64) NOT NULL
		REFERENCES galaxy(name),
	flag boolean NOT NULL,
	val float NOT NULL,
	error float,
	
	CONSTRAINT pk_flux_spitzer PRIMARY KEY (ion, galaxy)
)