-- public.model definition

-- Drop table

-- DROP TABLE model;

CREATE TABLE model (
	id bigserial NOT NULL,
	created_at timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP,
	is_deleted bool NOT NULL DEFAULT false,
	updated_at timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP,
	uuid uuid NULL,
	date_of_birth date NULL,
	local_name varchar(255) NULL,
	"name" varchar(255) NULL,
	year_of_birth int4 NULL,
	thumbnail varchar(255) NULL,
	url varchar(255) NULL,
	CONSTRAINT model_pkey PRIMARY KEY (id)
);
