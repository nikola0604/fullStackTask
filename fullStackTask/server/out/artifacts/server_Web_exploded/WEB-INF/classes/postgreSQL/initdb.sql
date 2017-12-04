SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS springdb;

CREATE DATABASE springdb ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';

ALTER DATABASE springdb OWNER TO postgres;

\connect springdb

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

COMMENT ON DATABASE springdb IS 'default administrative connection database';

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE client (
    id integer NOT NULL,
    clientname character varying(50),
    clientid character varying(100) NOT NULL,
    createdon timestamp without time zone
);


ALTER TABLE client OWNER TO postgres;

CREATE SEQUENCE client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE client_id_seq OWNER TO postgres;

ALTER SEQUENCE client_id_seq OWNED BY client.id;

CREATE TABLE filemetadata (
    id integer NOT NULL,
    filemetadataid character varying(100) NOT NULL,
    filename character varying(25),
    sourceid character varying(100),
    provider character varying(100),
    createdon timestamp without time zone
);


ALTER TABLE filemetadata OWNER TO postgres;

CREATE SEQUENCE filemetadata_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE filemetadata_id_seq OWNER TO postgres;

ALTER SEQUENCE filemetadata_id_seq OWNED BY filemetadata.id;

CREATE TABLE requirement (
    id integer NOT NULL,
    clientid character varying(100) NOT NULL,
    amount double precision,
    inputdate character varying(50),
    filemetadataid character varying(100) NOT NULL,
    createdon timestamp without time zone
);


ALTER TABLE requirement OWNER TO postgres;

CREATE SEQUENCE requirement_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE requirement_id_seq OWNER TO postgres;

ALTER SEQUENCE requirement_id_seq OWNED BY requirement.id;

ALTER TABLE ONLY client ALTER COLUMN id SET DEFAULT nextval('client_id_seq'::regclass);

ALTER TABLE ONLY filemetadata ALTER COLUMN id SET DEFAULT nextval('filemetadata_id_seq'::regclass);

ALTER TABLE ONLY requirement ALTER COLUMN id SET DEFAULT nextval('requirement_id_seq'::regclass);

SELECT pg_catalog.setval('requirement_id_seq', 1, true);

ALTER TABLE ONLY client
    ADD CONSTRAINT client_pkey PRIMARY KEY (clientid);

ALTER TABLE ONLY filemetadata
    ADD CONSTRAINT filemetadata_pkey PRIMARY KEY (filemetadataid);

ALTER TABLE ONLY requirement
    ADD CONSTRAINT requirement_pkey PRIMARY KEY (clientid, filemetadataid);

ALTER TABLE ONLY requirement
    ADD CONSTRAINT requirement_clientid_fkey FOREIGN KEY (clientid) REFERENCES client(clientid);

ALTER TABLE ONLY requirement
    ADD CONSTRAINT requirement_filemetadataid_fkey FOREIGN KEY (filemetadataid) REFERENCES filemetadata(filemetadataid);

GRANT ALL ON SCHEMA public TO PUBLIC;
