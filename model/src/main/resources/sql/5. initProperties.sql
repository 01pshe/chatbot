INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'SUSPEND_MODE', 'false');

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'CURRENT_DAY', '1');

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'REFRESH_QUESTION_TIME', '60');

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'RESULT_EXCELLENT_PCT', '80');

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'RESULT_GOOD_PCT', '50');

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'RESULT_BAD_PCT', '0');

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'MESSAGE_HANDLER_COUNT', '16');

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'DAY_QUESTION_CNT', '20');

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'DIFFICULT_QUESTION_CNT', '5');

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'MEDIUM_QUESTION_CNT', '7');

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'EASY_QUESTION_CNT', '8');