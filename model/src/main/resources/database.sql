CREATE TABLE bot_users
(
  id            SERIAL                    NOT NULL
    CONSTRAINT bot_users_pkey
    PRIMARY KEY,
  signature     VARCHAR(255)              NOT NULL,
  userfirstname VARCHAR(255)              NOT NULL,
  userlastname  VARCHAR(255),
  username      VARCHAR(255),
  bot           BOOLEAN DEFAULT FALSE,
  datecreate    DATE DEFAULT CURRENT_DATE NOT NULL
);

CREATE UNIQUE INDEX bot_users_signature_uindex
  ON bot_users (signature);


CREATE TABLE bot_messages
(
  id      SERIAL  NOT NULL
    CONSTRAINT bot_messages_pkey
    PRIMARY KEY,
  user_id INTEGER NOT NULL
    CONSTRAINT bot_messages_user_id_fkey
    REFERENCES bot_users,
  date    SERIAL  NOT NULL,
  message TEXT
);

CREATE TABLE bot_userinfo
(
  id          SERIAL  NOT NULL
    CONSTRAINT bot_userinfo_pkey
    PRIMARY KEY,
  name        VARCHAR(255),
  surname     VARCHAR(255),
  middlename  VARCHAR(255),
  position    VARCHAR(255),
  company     VARCHAR(255),
  phonenumber VARCHAR(255),
  email       VARCHAR(255)
);

CREATE TABLE bot_scenario
(
  id      SERIAL  NOT NULL
    CONSTRAINT bot_scenario_pkey
    PRIMARY KEY,
  current_step INTEGER
);

CREATE TABLE bot_step
(
  id          SERIAL  NOT NULL
    CONSTRAINT bot_step_pkey
    PRIMARY KEY,
  step        INTEGER NOT NULL,
  substep     INTEGER,
  description VARCHAR(255)
);

CREATE TABLE bot_scenariostep
(
  id      SERIAL  NOT NULL
    CONSTRAINT bot_scenariostep_pkey
    PRIMARY KEY,
  step_number INTEGER,
  step_id INTEGER REFERENCES bot_step(id),
  scenario_id INTEGER REFERENCES bot_scenario(id),
  UNIQUE (scenario_id,step_number)
);



commit