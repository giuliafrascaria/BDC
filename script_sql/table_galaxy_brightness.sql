CREATE TABLE galaxy_brightness
(
	galaxy character varying(64)
		REFERENCES galaxy(name),
	ion character varying(64)
		REFERENCES brightness(ion),
	
	CONSTRAINT pk_galaxy_brightness PRIMARY KEY(galaxy, ion)
)