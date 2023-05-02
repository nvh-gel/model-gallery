-- public."role" definition

-- Drop table

DROP TABLE "role";

CREATE TABLE "role" (
	id bigserial NOT NULL,
	created_at timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP,
	is_deleted bool NOT NULL DEFAULT false,
	updated_at timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP,
	uuid uuid NULL,
	"name" varchar(255) NULL,
	CONSTRAINT role_pkey PRIMARY KEY (id)
);
