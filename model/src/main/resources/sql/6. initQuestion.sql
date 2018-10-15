INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight)
VALUES (nextval('hibernate_sequence'), 'LongConsumer', 'LongFunction<R>', 'LongPredicate', 'LongSupplier', 'C',
        'LongPredicate',
        'Which of the following functional interface represents a predicate (Boolean-valued function) of one long-valued argument?',
        1, 'EASY');

INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight)
VALUES (nextval('hibernate_sequence'), 'ObjIntConsumer<T>', 'ObjLongConsumer<T>', 'Predicate<T>', 'Supplier<T>', 'D',
        'Supplier<T>', 'Which of the following functional interface represents a supplier of results', 1, 'EASY');

INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight)
VALUES (nextval('hibernate_sequence'), 'LongConsumer', 'LongFunction<R>', 'LongPredicate', 'LongSupplier', 'B',
        'LongFunction<R>',
        'Which of the following functional interface represents a function that accepts a long-valued argument and produces a result?',
        1, 'EASY');

INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight)
VALUES (nextval('hibernate_sequence'), 'false', 'true', '', '', 'B', 'true',
        'A method reference is described using :: (double colon) symbol.', 2, 'EASY');

INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight)
VALUES (nextval('hibernate_sequence'), 'false', 'true', '', '', 'B', 'true',
        'Stream operations do the iterations internally over the source elements provided.', 2, 'EASY');

INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight)
VALUES (nextval('hibernate_sequence'), 'ToDoubleBiFunction<T,U>', 'ToDoubleFunction<T>', 'ToIntBiFunction<T,U>',
        'ToIntFunction<T>', 'A', 'ToDoubleBiFunction<T,U>',
        'Which of the following functional interface represents a function that accepts two arguments and produces a double-valued result?',
        2, 'MEDIUM');

INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight)
VALUES (nextval('hibernate_sequence'), '(int a, int b) -> a + b;', '(a, b) -> a + b', 'Both of the above.',
        'None of the above.', 'C', 'Both of the above.',
        'Which of the following is the correct lambda expression which add two numbers and return their sum?', 1,
        'MEDIUM');

INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight)
VALUES (nextval('hibernate_sequence'), 'getDecoder()', 'getEncoder()', 'getMimeDecoder()', 'getMimeEncoder', 'C',
        'getMimeDecoder()',
        'Which of the following method of Base64 class returns a Base64.Decoder that decodes using the MIME type base64 decoding scheme?',
        2, 'MEDIUM');

INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight)
VALUES (nextval('hibernate_sequence'), 'false', 'true', '', '', 'B', 'true',
        'Using lambda expression, you can refer to final variable or effectively final variable (which is assigned only once).',
        1, 'DIFFICULT');

INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight)
VALUES (nextval('hibernate_sequence'), 'Ошибка компиляции', '«Object»', '«IOException»', '«FileNotFoundException»', 'D',
        '«FileNotFoundException»', 'Имеется следующий код:
public class Overload{
  public void method(Object o) {
    System.out.println(""Object"");
  }
  public void method(java.io.FileNotFoundException f) {
    System.out.println(""FileNotFoundException"");
  }
  public void method(java.io.IOException i) {
    System.out.println(""IOException"");
  }
  public static void main(String args[]) {
    Overload test = new Overload();
    test.method(null);
  }
}

Результатом его компиляции и выполнения будет:', 2, 'DIFFICULT');

INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight)
VALUES (nextval('hibernate_sequence'), 'true true', 'false false', 'true false', 'false true', 'G', 'true false', 'Integer a = 120;
Integer b = 120;
Integer c = 130;
Integer d = 130;
System.out.println(a==b);
System.out.println(c==d);

В результате выполнения данного кода будет выведено:', 1, 'MEDIUM');
