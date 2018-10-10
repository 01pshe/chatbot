-- Grant users group to work with objects --
GRANT USAGE,SELECT ON SEQUENCE hibernate_sequence TO bot_user_role;
GRANT SELECT, INSERT, UPDATE ON TABLE bot_answer TO bot_user_role;
GRANT SELECT, INSERT, UPDATE ON TABLE bot_messages TO bot_user_role;
GRANT SELECT, INSERT, UPDATE ON TABLE bot_properties TO bot_user_role;
GRANT SELECT, INSERT, UPDATE ON TABLE bot_question TO bot_user_role;
GRANT SELECT, INSERT, UPDATE ON TABLE bot_users TO bot_user_role;

-- Change owner to admin group --
ALTER TABLE bot_answer OWNER TO bot_admin_role;
ALTER TABLE bot_messages OWNER TO bot_admin_role;
ALTER TABLE bot_properties OWNER TO bot_admin_role;
ALTER TABLE bot_question OWNER TO bot_admin_role;
ALTER TABLE bot_users OWNER TO bot_admin_role;
ALTER SEQUENCE hibernate_sequence OWNER TO bot_admin_role;