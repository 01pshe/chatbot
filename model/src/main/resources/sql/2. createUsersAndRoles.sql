CREATE ROLE bot_admin_role WITH NOLOGIN;
CREATE USER bot_admin WITH PASSWORD 'bot_admin';
GRANT bot_admin_role to bot_admin;

CREATE ROLE bot_user_role WITH NOLOGIN;
CREATE USER bot_user WITH PASSWORD  'bot_user';
GRANT bot_user_role to bot_user;
