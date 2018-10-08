create sequence hibernate_sequence start 1 increment 1;

create table bot_answer (
  id int8 not null,
  answer varchar(255) not null,
  question_id int8,
  user_id int8,
  primary key (id)
);

create table bot_messages (
  id int8 not null,
  date int8 not null,
  inbound boolean,
  message varchar(255) not null,
  user_id int8,
  primary key (id)
);

create table bot_properties (
  id  bigserial not null,
  property_name varchar(255) not null,
  property_value varchar(255),
  primary key (id)
);

create table bot_question (
  id int8 not null,
  answerA varchar(2048),
  answerB varchar(2048),
  answerC varchar(2048),
  answerD varchar(2048),
  answerR varchar(2048),
  answerRight varchar(2048),
  question varchar(4096) not null,
  useDay int4,
  weight varchar(9) not null,
  primary key (id)
);

create table bot_scenario (
  id int8 not null,
  current_step int4,
  user_id int8,
  primary key (id)
);

create table bot_scenariostep (
  id int8 not null,
  step_number int4 not null,
  scenario_id int8,
  step_id int8,
  primary key (id)
);

create table bot_step (
  id int8 not null,
  description varchar(255),
  executor_class varchar(255),
  need_answer boolean,
  step int8,
  substep int8,
  primary key (id)
);

create table bot_userinfo (
  id int8 not null,
  company varchar(255),
  email varchar(255),
  middleName varchar(255),
  name varchar(255),
  phoneNumber varchar(255),
  position varchar(255),
  surname varchar(255),
  user_id int8,
  primary key (id)
);

create table bot_users (
  id int8 not null,
  bot boolean,
  datecreate timestamp,
  signature varchar(255) not null,
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

alter table if exists bot_scenario
  add constraint FK24e6x4llleyjilsnirwn7wn4u
foreign key (user_id)
references bot_users;

alter table if exists bot_scenariostep
  add constraint FKhds01b6ttofdg38211jdeamyv
foreign key (scenario_id)
references bot_scenario;

alter table if exists bot_scenariostep
  add constraint FKffmpwh0a426kx9bbmi18k2tm7
foreign key (step_id)
references bot_step;

alter table if exists bot_userinfo
  add constraint FKd03es9dgja438xiooxrgidlri
foreign key (user_id)
references bot_users;