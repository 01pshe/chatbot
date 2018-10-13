INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'SUSPEND_MODE', 'false');

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'CURRENT_DAY', '1');

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'REFRESH_QUESTION_TIME', '60');

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'EXCELLENT_RESULT', '80');

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'GOOD_RESULT', '50');

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'BAD_RESULT', '0');
