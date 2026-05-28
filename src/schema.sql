-- public.tasks definition

-- Drop table

-- DROP TABLE public.tasks;

CREATE TABLE public.tasks (
	title varchar NOT NULL,
	description varchar NULL,
	status varchar NOT NULL,
	due_timestamp timestamptz NOT NULL,
	created_timestamp timestamptz NOT NULL,
	updated_timestamp timestamptz NOT NULL,
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	CONSTRAINT tasks_pkey PRIMARY KEY (id)
);
