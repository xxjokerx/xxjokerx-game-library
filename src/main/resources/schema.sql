DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE SEQUENCE hibernate_sequence START 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS category
(
    id              BIGINT      NOT NULL NOT NULL DEFAULT nextval('hibernate_sequence'),
    lower_case_name VARCHAR(50) NOT NULL,
    name            VARCHAR(50) NOT NULL,
    CONSTRAINT category_pkey
        PRIMARY KEY (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS uniq_cat ON category (lower_case_name);
ALTER TABLE category
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS contact
(
    id            BIGINT      NOT NULL NOT NULL DEFAULT nextval('hibernate_sequence'),
    city          VARCHAR(50),
    country       VARCHAR(50) NOT NULL,
    mail_address  VARCHAR(320),
    phone_number  VARCHAR(50),
    postal_code   VARCHAR(50),
    street        VARCHAR(255),
    street_number VARCHAR(10),
    website       VARCHAR(75),
    CONSTRAINT contact_pkey
        PRIMARY KEY (id)
);

ALTER TABLE contact
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS account
(
    id                BIGINT       NOT NULL NOT NULL DEFAULT nextval('hibernate_sequence'),
    first_name        VARCHAR(127),
    last_name         VARCHAR(127),
    membership_number VARCHAR(50)  NOT NULL,
    renewal_date      DATE,
    username          VARCHAR(255) NOT NULL,
    fk_contact        BIGINT,
    CONSTRAINT account_pkey
        PRIMARY KEY (id),
    FOREIGN KEY (fk_contact) REFERENCES contact
);
CREATE UNIQUE INDEX IF NOT EXISTS uniq_acc ON account (username);
ALTER TABLE account
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS creator
(
    id                    BIGINT      NOT NULL NOT NULL DEFAULT nextval('hibernate_sequence'),
    first_name            VARCHAR(50),
    last_name             VARCHAR(50) NOT NULL,
    lower_case_first_name VARCHAR(50) NOT NULL,
    lower_case_last_name  VARCHAR(50) NOT NULL,
    role                  INTEGER     NOT NULL,
    fk_contact            BIGINT,
    CONSTRAINT creator_pkey
        PRIMARY KEY (id),
    FOREIGN KEY (fk_contact) REFERENCES contact
);

CREATE UNIQUE INDEX IF NOT EXISTS unique_name ON creator (lower_case_first_name, lower_case_last_name);
ALTER TABLE creator
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS loan_status
(
    id             BIGINT       NOT NULL NOT NULL DEFAULT nextval('hibernate_sequence'),
    description    VARCHAR(255) NOT NULL,
    lower_case_tag VARCHAR(50)  NOT NULL,
    tag            VARCHAR(50)  NOT NULL,
    CONSTRAINT loan_status_pkey
        PRIMARY KEY (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS lower_case_tag ON loan_status (lower_case_tag);
ALTER TABLE loan_status
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS product_line
(
    id              BIGINT       NOT NULL NOT NULL DEFAULT nextval('hibernate_sequence'),
    lower_case_name VARCHAR(255) NOT NULL,
    name            VARCHAR(255) NOT NULL,
    CONSTRAINT product_line_pkey
        PRIMARY KEY (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS uniq_pro ON product_line (lower_case_name);
ALTER TABLE product_line
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS game
(
    id                   BIGINT       NOT NULL NOT NULL DEFAULT nextval('hibernate_sequence'),
    core_rules           oid,
    description          VARCHAR(1000),
    edition_number       VARCHAR(255),
    ending               oid,
    goal                 VARCHAR(1000),
    lower_case_name      VARCHAR(255) NOT NULL,
    max_age              SMALLINT     NOT NULL,
    max_number_of_player SMALLINT     NOT NULL,
    min_age              SMALLINT     NOT NULL,
    min_month            SMALLINT     NOT NULL,
    min_number_of_player SMALLINT     NOT NULL,
    name                 VARCHAR(255) NOT NULL,
    nature               INTEGER,
    play_time            VARCHAR(20),
    preparation          oid,
    size                 VARCHAR(255),
    stuff                VARCHAR(1000),
    variant              oid,
    core_game_id         BIGINT,
    fk_product_line      BIGINT,
    CONSTRAINT game_pkey
        PRIMARY KEY (id),
    FOREIGN KEY (core_game_id) REFERENCES game,
    FOREIGN KEY (fk_product_line) REFERENCES product_line
);
CREATE UNIQUE INDEX IF NOT EXISTS uniq_gam ON game (lower_case_name);
ALTER TABLE game
    OWNER TO postgres;

ALTER TABLE game
    ADD CONSTRAINT game_max_age_check
        CHECK ((max_age >= 0) AND (max_age <= 100));

ALTER TABLE game
    ADD CONSTRAINT game_max_number_of_player_check
        CHECK ((max_number_of_player >= 0) AND (max_number_of_player <= 100));

ALTER TABLE game
    ADD CONSTRAINT game_min_age_check
        CHECK ((min_age >= 0) AND (min_age <= 100));

ALTER TABLE game
    ADD CONSTRAINT game_min_month_check
        CHECK ((min_month >= 0) AND (min_month <= 100));

ALTER TABLE game
    ADD CONSTRAINT game_min_number_of_player_check
        CHECK ((min_number_of_player >= 1) AND (min_number_of_player <= 100));

CREATE TABLE IF NOT EXISTS image
(
    id      BIGINT NOT NULL NOT NULL DEFAULT nextval('hibernate_sequence'),
    data    oid    NOT NULL,
    fk_game BIGINT,
    CONSTRAINT image_pkey
        PRIMARY KEY (id),
    FOREIGN KEY (fk_game) REFERENCES game
);

ALTER TABLE image
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS publisher
(
    id              BIGINT       NOT NULL NOT NULL DEFAULT nextval('hibernate_sequence'),
    lower_case_name VARCHAR(255) NOT NULL,
    name            VARCHAR(255) NOT NULL,
    fk_contact      BIGINT,
    CONSTRAINT publisher_pkey
        PRIMARY KEY (id),
    FOREIGN KEY (fk_contact) REFERENCES contact
);
CREATE UNIQUE INDEX IF NOT EXISTS uniq_pub ON publisher (lower_case_name);
ALTER TABLE publisher
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS seller
(
    id              BIGINT       NOT NULL NOT NULL DEFAULT nextval('hibernate_sequence'),
    lower_case_name VARCHAR(255) NOT NULL,
    name            VARCHAR(255) NOT NULL,
    fk_contact      BIGINT,
    CONSTRAINT seller_pkey
        PRIMARY KEY (id),
    FOREIGN KEY (fk_contact) REFERENCES contact
);
CREATE UNIQUE INDEX IF NOT EXISTS uniq_sel ON seller (lower_case_name);
ALTER TABLE seller
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS game_copy
(
    id               BIGINT       NOT NULL NOT NULL DEFAULT nextval('hibernate_sequence'),
    date_of_purchase DATE,
    general_state    INTEGER      NOT NULL,
    is_loanable      BOOLEAN,
    location         VARCHAR(255),
    object_code      VARCHAR(255) NOT NULL,
    price            NUMERIC(12, 2),
    register_date    date         NOT NULL,
    wear_condition   VARCHAR(255) NOT NULL,
    fk_game          BIGINT       NOT NULL,
    fk_publisher     BIGINT,
    fk_seller        BIGINT,
    CONSTRAINT game_copy_pkey
        PRIMARY KEY (id),
    FOREIGN KEY (fk_game) REFERENCES game,
    FOREIGN KEY (fk_publisher) REFERENCES publisher,
    FOREIGN KEY (fk_seller) REFERENCES seller
);
CREATE UNIQUE INDEX IF NOT EXISTS uniq_obj ON game_copy (object_code);
ALTER TABLE game_copy
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS loan
(
    id              BIGINT  NOT NULL NOT NULL DEFAULT nextval('hibernate_sequence'),
    is_closed       BOOLEAN NOT NULL,
    loan_end_time   date    NOT NULL,
    loan_start_time date    NOT NULL,
    fk_account      BIGINT,
    fk_game_copy    BIGINT,
    CONSTRAINT loan_pkey
        PRIMARY KEY (id),
    FOREIGN KEY (fk_account) REFERENCES account,
    FOREIGN KEY (fk_game_copy) REFERENCES game_copy
);
ALTER TABLE loan
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS theme
(
    id              BIGINT      NOT NULL NOT NULL DEFAULT nextval('hibernate_sequence'),
    lower_case_name VARCHAR(50) NOT NULL,
    name            VARCHAR(50) NOT NULL,
    CONSTRAINT theme_pkey
        PRIMARY KEY (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS uniq_the ON theme (lower_case_name);
ALTER TABLE theme
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS game_category
(
    fk_game     BIGINT NOT NULL,
    fk_category BIGINT NOT NULL,
    CONSTRAINT game_category_pkey
        PRIMARY KEY (fk_game, fk_category),
    FOREIGN KEY (fk_category) REFERENCES category,
    FOREIGN KEY (fk_game) REFERENCES game
);
ALTER TABLE game_category
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS game_creator
(
    fk_game    BIGINT NOT NULL,
    fk_creator BIGINT NOT NULL,
    CONSTRAINT game_creator_pkey
        PRIMARY KEY (fk_game, fk_creator),
    FOREIGN KEY (fk_creator) REFERENCES creator,
    FOREIGN KEY (fk_game) REFERENCES game
);
ALTER TABLE game_creator
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS game_theme
(
    fk_game  BIGINT NOT NULL,
    fk_theme BIGINT NOT NULL,
    CONSTRAINT game_theme_pkey
        PRIMARY KEY (fk_game, fk_theme),
    FOREIGN KEY (fk_theme) REFERENCES theme,
    FOREIGN KEY (fk_game) REFERENCES game
);
ALTER TABLE game_theme
    OWNER TO postgres;


