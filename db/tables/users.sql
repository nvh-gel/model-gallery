-- public.users definition

-- Drop table

DROP TABLE users;

CREATE TABLE users (
	id bigserial NOT NULL,
	created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
	is_deleted bool NULL DEFAULT false,
	updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
	uuid uuid NULL,
	enabled bool NOT NULL,
	"password" varchar(255) NULL,
	username varchar(255) NULL,
	email varchar(255) NULL,
	CONSTRAINT con_unique_username UNIQUE (username),
	CONSTRAINT users_pkey PRIMARY KEY (id)
);
