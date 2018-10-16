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
VALUES (nextval('hibernate_sequence'), 'EXCELLENT_RESULT',
        'Молодец *%s* \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\n\n ✅ Твой результат - %.2f процентов!\n\nПриходи на стенд СберТеха, покажи свой результат и получи приз. \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\n\nА еще ты можешь принять участие в розыгрыше главного приза\uD83D\uDC4C.\nРозыгрыш пройдёт на стенде СберТеха 20 октября в 17:10!'
);

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'GOOD_RESULT',
        'Молодец *%s* \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\n\n ✅ Твой результат - %.2f процентов!\n\nПриходи на стенд СберТеха, покажи свой результат и получи приз.\uD83C\uDF81\uD83C\uDF81\uD83C\uDF81'
);

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'BAD_RESULT',
        'Молодец *%s* \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\n\n ✅ Твой результат - %.2f процентов!\n\nСпасибо что принял участие в нашем тесте! \uD83D\uDE09'
);

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'SUSPEND_TEXT',
        'На сегодня тестирование окончено,\nпора сделать перерыв ✨\uD83D\uDE34\uD83C\uDF19 \nЗавтра появятся новые вопросы!'
);

INSERT INTO public.bot_properties (id, property_name, property_value)
VALUES (nextval('hibernate_sequence'), 'WELCOME_TEXT',
        'Привет, *%s*! \uD83D\uDC4B \nЗдорово, что ты решился пройти тест на знание Java. \uD83D\uDCDD \nТест проводится два дня, каждый день новые вопросы и возможность получить приз. \nНабери максимум баллов и ты сможешь участвовать в финальном розыгрыше главного приза. \nБудь лучшим, удачи.\uD83C\uDF81 \n'
);
