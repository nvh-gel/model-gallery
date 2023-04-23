-- nickname definition

-- Drop table

-- DROP TABLE nickname;

CREATE TABLE nickname
(
    id         bigserial    NOT NULL,
    created_at timestamp(6) NULL     DEFAULT CURRENT_TIMESTAMP,
    is_deleted bool         NOT NULL DEFAULT false,
    updated_at timestamp(6) NULL     DEFAULT CURRENT_TIMESTAMP,
    uuid       uuid         NULL,
    nick       varchar(255) NULL,
    model_id   int8         NULL,
    CONSTRAINT nickname_pkey PRIMARY KEY (id)
);


-- public.nickname foreign keys

ALTER TABLE nickname
    ADD CONSTRAINT fk_nickname_model_id FOREIGN KEY (model_id) REFERENCES model (id);
