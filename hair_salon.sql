--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: clients; Type: TABLE; Schema: public; Owner: iliak; Tablespace: 
--

CREATE TABLE clients (
    id integer NOT NULL,
    first_name character varying(20),
    last_name character varying(20),
    stylist_id integer,
    phone_number character varying(18)
);


ALTER TABLE clients OWNER TO iliak;

--
-- Name: clients_id_seq; Type: SEQUENCE; Schema: public; Owner: iliak
--

CREATE SEQUENCE clients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE clients_id_seq OWNER TO iliak;

--
-- Name: clients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: iliak
--

ALTER SEQUENCE clients_id_seq OWNED BY clients.id;


--
-- Name: stylists; Type: TABLE; Schema: public; Owner: iliak; Tablespace: 
--

CREATE TABLE stylists (
    id integer NOT NULL,
    first_name character varying(20),
    last_name character varying(20)
);


ALTER TABLE stylists OWNER TO iliak;

--
-- Name: stylists_id_seq; Type: SEQUENCE; Schema: public; Owner: iliak
--

CREATE SEQUENCE stylists_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE stylists_id_seq OWNER TO iliak;

--
-- Name: stylists_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: iliak
--

ALTER SEQUENCE stylists_id_seq OWNED BY stylists.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: iliak
--

ALTER TABLE ONLY clients ALTER COLUMN id SET DEFAULT nextval('clients_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: iliak
--

ALTER TABLE ONLY stylists ALTER COLUMN id SET DEFAULT nextval('stylists_id_seq'::regclass);


--
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: iliak
--

COPY clients (id, first_name, last_name, stylist_id, phone_number) FROM stdin;
\.


--
-- Name: clients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: iliak
--

SELECT pg_catalog.setval('clients_id_seq', 40, true);


--
-- Data for Name: stylists; Type: TABLE DATA; Schema: public; Owner: iliak
--

COPY stylists (id, first_name, last_name) FROM stdin;
\.


--
-- Name: stylists_id_seq; Type: SEQUENCE SET; Schema: public; Owner: iliak
--

SELECT pg_catalog.setval('stylists_id_seq', 52, true);


--
-- Name: clients_pkey; Type: CONSTRAINT; Schema: public; Owner: iliak; Tablespace: 
--

ALTER TABLE ONLY clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- Name: stylists_pkey; Type: CONSTRAINT; Schema: public; Owner: iliak; Tablespace: 
--

ALTER TABLE ONLY stylists
    ADD CONSTRAINT stylists_pkey PRIMARY KEY (id);


--
-- Name: clients_stylist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: iliak
--

ALTER TABLE ONLY clients
    ADD CONSTRAINT clients_stylist_id_fkey FOREIGN KEY (stylist_id) REFERENCES stylists(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

