CREATE SEQUENCE hibernate_sequence
  START 1
  INCREMENT 1;

CREATE TABLE bot_answer (
  id          INT8 NOT NULL,
  user_answer      VARCHAR(255),
  question_id INT8,
  user_id     INT8,
  PRIMARY KEY (id)
);

CREATE TABLE bot_messages (
  id      INT8         NOT NULL,
  date    INT8         NOT NULL,
  inbound BOOLEAN,
  message VARCHAR(255) NOT NULL,
  user_id INT8,
  PRIMARY KEY (id)
);

CREATE TABLE bot_properties (
  id             INT8    NOT NULL,
  property_name  VARCHAR(255) NOT NULL,
  property_value VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE bot_question (
  id          INT8          NOT NULL,
  answerA     VARCHAR(60),
  answerB     VARCHAR(60),
  answerC     VARCHAR(60),
  answerD     VARCHAR(60),
  answerR     VARCHAR(60),
  answerRight VARCHAR(60),
  question    VARCHAR(4096) NOT NULL,
  useDay      INT4,
  weight      VARCHAR(9)    NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE bot_users (
  id            INT8         NOT NULL,
  bot           BOOLEAN,
  datecreate    TIMESTAMP,
  signature     VARCHAR(255) NOT NULL,
  userfirstname VARCHAR(255),
  userlastname  VARCHAR(255),
  username      VARCHAR(255),
  PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS bot_properties
  ADD CONSTRAINT UK_gqfvw1943s4avwtp30y26puih UNIQUE (property_name);

ALTER TABLE IF EXISTS bot_question
  ADD CONSTRAINT UK_kots2hu1xmk549nm6ykp0iql3 UNIQUE (question);

ALTER TABLE IF EXISTS bot_users
  ADD CONSTRAINT UK_dt0j4337g8o5c5a64ei5o3uk7 UNIQUE (signature);

ALTER TABLE IF EXISTS bot_answer
  ADD CONSTRAINT FK44r5hv1sxw9vdro9mxekronr7
FOREIGN KEY (question_id)
REFERENCES bot_question;

ALTER TABLE IF EXISTS bot_answer
  ADD CONSTRAINT FK254ui3uk9pjx2h0gxmw6casek
FOREIGN KEY (user_id)
REFERENCES bot_users;

ALTER TABLE IF EXISTS bot_messages
  ADD CONSTRAINT FKj5sq3dyngy8wlui7xghc27jir
FOREIGN KEY (user_id)
REFERENCES bot_users;
