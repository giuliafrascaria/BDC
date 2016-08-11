CREATE TABLE flux_pacs
(
	ion character varying(64) NOT NULL,
	galaxy character varying(64) NOT NULL 
		REFERENCES galaxy(name),
	aperture smallint NOT NULL,
	flag boolean NOT NULL,
	val float NOT NULL,
	error float,
	
	CONSTRAINT pk_flux_pacs PRIMARY KEY (ion, aperture, galaxy)
)