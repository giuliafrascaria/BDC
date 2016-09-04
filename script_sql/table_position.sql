CREATE TABLE position
(
	raH real NOT NULL,
	raM real NOT NULL,
	raS real NOT NULL,
	deSgn boolean NOT NULL,
	deD real NOT NULL,
	deM real NOT NULL,
	deS real NOT NULL,
	redShift real NOT NULL,
	galaxy character varying(64)
		REFERENCES galaxy(name),
		
	CONSTRAINT pk_position PRIMARY KEY (galaxy)
	
)
