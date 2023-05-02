-- public.nickname definition

-- Drop table

-- DROP TABLE nickname;

CREATE TABLE nickname (
	id bigserial NOT NULL,
	created_at timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP,
	is_deleted bool NOT NULL DEFAULT FALSE,
	updated_at timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP,
	uuid uuid NULL,
	nick varchar(255) NULL,
	model_id int8 NULL,
	url varchar(255) NULL,
	CONSTRAINT nickname_pkey PRIMARY KEY (id),
	CONSTRAINT fk_nickname_model FOREIGN KEY (model_id) REFERENCES model(id)
);

CREATE INDEX nickname_nick_idx ON nickname (nick);
