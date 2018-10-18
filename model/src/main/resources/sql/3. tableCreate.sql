create sequence hibernate_sequence start 1 increment 1;

create table bot_answer (
  id int8 not null,
  dateCreate timestamp,
  dateUserAnswer timestamp,
  day_answer int4,
  result varchar(255),
  user_answer varchar(255),
  user_name varchar(255),
  weight varchar(9) not null,
  question_id int8,
  user_id int8,
  primary key (id)
);

create table bot_messages (
  id int8 not null,
  date timestamp not null,
  inbound boolean,
  message varchar(4096) not null,
  nameUser varchar(255),
  user_id int8,
  primary key (id)
);

create table bot_properties (
  id int8 not null,
  property_name varchar(255) not null,
  property_value varchar(4096),
  primary key (id)
);

create table bot_question (
  id int8 not null,
  answerA varchar(35),
  answerB varchar(35),
  answerC varchar(35),
  answerD varchar(35),
  answerR varchar(35),
  answerRight varchar(35),
  question varchar(4096) not null,
  useDay int4,
  weight varchar(9) not null,
  primary key (id)
);

create table bot_users (
  id int8 not null,
  bot boolean,
  chat_id int8,
  dateCreate timestamp,
  day_one_result Decimal(10,2) default '00.00',
  day_two_result Decimal(10,2) default '00.00',
  pass_day int4,
  signature varchar(255) not null,
  start_first boolean,
  total_result Decimal(10,2) default '00.00',
  userfirstname varchar(255),
  userlastname varchar(255),
  username varchar(255),
  primary key (id)
);

alter table if exists bot_properties
  add constraint UK_gqfvw1943s4avwtp30y26puih unique (property_name);

alter table if exists bot_question
  add constraint UK_kots2hu1xmk549nm6ykp0iql3 unique (question);

alter table if exists bot_users
  add constraint UK_dt0j4337g8o5c5a64ei5o3uk7 unique (signature);

alter table if exists bot_answer
  add constraint FK44r5hv1sxw9vdro9mxekronr7
foreign key (question_id)
references bot_question;

alter table if exists bot_answer
  add constraint FK254ui3uk9pjx2h0gxmw6casek
foreign key (user_id)
references bot_users;

alter table if exists bot_messages
  add constraint FKj5sq3dyngy8wlui7xghc27jir
foreign key (user_id)
references bot_users;