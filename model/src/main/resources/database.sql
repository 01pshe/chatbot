-- auto-generated definition
CREATE TABLE bot_users
(
  id         INTEGER NOT NULL
    CONSTRAINT bot_users_pkey
    PRIMARY KEY,
  signature           VARCHAR(255)                                  NOT NULL,
  userfirstname       VARCHAR(255)                                  NOT NULL,
  userLastName        VARCHAR(255)                                  NOT NULL,
  userName            VARCHAR(255)                                  NOT NULL,
  bot                 BOOLEAN DEFAULT FALSE,
  datecreate DATE DEFAULT CURRENT_DATE                     NOT NULL
);

CREATE UNIQUE INDEX bot_users_signature_uindex
  ON bot_users (signature);

-- auto-generated definition
CREATE TABLE bot_messages
(
  id         INTEGER NOT NULL
    CONSTRAINT bot_messages_pkey
    PRIMARY KEY,
  user_id INTEGER NOT NULL
    CONSTRAINT bot_messages_user_id_fkey
    REFERENCES bot_users,
  date                SERIAL,
  message             TEXT
);

commit