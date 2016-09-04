CREATE TABLE flux_cont
(
	ion character varying(64) NOT NULL,
	galaxy character varying(64) NOT NULL
		REFERENCES galaxy(name),
	val real NOT NULL,
	flag boolean NOT NULL,
	aperture character varying(64) NOT NULL,
	error real,
	
	CONSTRAINT pk_flux_cont PRIMARY KEY (ion, galaxy)
)