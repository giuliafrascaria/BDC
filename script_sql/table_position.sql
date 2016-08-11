CREATE TABLE position
(
	raH float NOT NULL,
	raM float NOT NULL,
	raS float NOT NULL,
	deSgn boolean NOT NULL,
	deD float NOT NULL,
	deM float NOT NULL,
	deS float NOT NULL,
	redShift float NOT NULL,
	galaxy character varying(64)
		REFERENCES galaxy(name),
		
	CONSTRAINT pk_position PRIMARY KEY (galaxy)
	
)
