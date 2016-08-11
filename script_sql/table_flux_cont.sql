CREATE TABLE flux_cont
(
	ion character varying(64) NOT NULL,
	galaxy character varying(64) 
		REFERENCES galaxy(name),
	val float NOT NULL,
	aperture smallint NOT NULL,
	error float,
	
	CONSTRAINT pk_flux_cont PRIMARY KEY (ion, galaxy)
)