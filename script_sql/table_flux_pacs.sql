CREATE TABLE flux_pacs
(
	ion character varying(64) NOT NULL,
	galaxy character varying(64) NOT NULL 
		REFERENCES galaxy(name),
	aperture character varying(64) NOT NULL,
	flag boolean NOT NULL,
	val real NOT NULL,
	error real,
	
	CONSTRAINT pk_flux_pacs PRIMARY KEY (ion, aperture, galaxy)
)