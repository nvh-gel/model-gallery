-- public.authorities definition

-- Drop table

DROP TABLE authorities;

CREATE TABLE authorities (
	id bigserial NOT NULL,
	created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
	is_deleted bool NULL DEFAULT false,
	updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
	uuid uuid NULL,
	authority varchar(255) NULL,
	username varchar(255) NULL,
	CONSTRAINT authorities_pkey PRIMARY KEY (id),
	CONSTRAINT fk_authority_user FOREIGN KEY (username) REFERENCES users(username)
);
