-- public.model definition

-- Drop table

-- DROP TABLE model;

CREATE TABLE model (
	id bigserial NOT NULL,
	created_at timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP,
	is_deleted bool NOT NULL DEFAULT FALSE,
	updated_at timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP,
	uuid uuid NULL,
	date_of_birth date NULL,
	name varchar(255) NULL,
	thumbnail varchar(255) NULL,
	url varchar(255) NULL,
	year_of_birth int4 NULL,
	description text NULL,
	native_name varchar(255) NULL,
	boob int4 NULL,
	hip int4 NULL,
	waist int4 NULL,
	CONSTRAINT model_pkey PRIMARY KEY (id)
);
