INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (56, ''Ошибка компиляции'', ''«Object»'', ''«IOException»'', ''«FileNotFoundException»'', ''D'', ''«FileNotFoundException»'', ''*Какой будет результат выполнения программы?*

```public class Overload{
  public void method(Object o) {
    System.out.println("Object");
  }
  public void method(java.io.FileNotFoundException f) {
    System.out.println("FileNotFoundException");
  }
  public void method(java.io.IOException i) {
    System.out.println("IOException");
  }
  public static void main(String args[]) {
    Overload test = new Overload();
    test.method(null);
  }
}```'', 1, ''DIFFICULT'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (62, ''Zero'', ''Twelve'', ''Default'', ''Ошибка компиляции.'', ''D'', ''Ошибка компиляции'', ''*Какой будет результат выполнения программы?*
```
Float f = new Float("12");
switch (f)
{
    case 12: System.out.println("Twelve");
    case 0: System.out.println("Zero");
    default: System.out.println("Default");
}```'', 1, ''MEDIUM'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (63, ''finally'', ''exception finished'', ''finally exception finished'', ''Ошибка компиляции'', ''C'', ''finally exception finished'', ''*Какой будет результат выполнения программы?*
```
public class Test
{
    public static void aMethod() throws Exception
    {
        try
        {
            throw new Exception();
        }
        finally
        {
            System.out.print("finally ");
        }
    }
    public static void main(String args[])
    {
        try
        {
            aMethod();
        }
        catch (Exception e)
        {
            System.out.print("exception ");
        }
        System.out.print("finished");
    }
}```'', 1, ''MEDIUM'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (65, ''Ошибка компиляции.'', ''Ошибка выполнения.'', ''Thread one. Thread two.'', ''Вывод не может быть определен.'', ''B'', ''Ошибка выполнения.'', ''*Какой будет результат выполнения программы?*
```
class MyThread extends Thread
{
    public static void main(String [] args)
    {
        MyThread t = new MyThread();
        t.start();
        System.out.print("one. ");
        t.start();
        System.out.print("two. ");
    }
    public void run()
    {
        System.out.print("Thread ");
    }
}```'', 1, ''MEDIUM'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (66, ''DeadLock'', ''12 12 12 12'', ''Ошибка компиляции'', ''Выход не может быть определен.'', ''B'', ''12 12 12 12'', ''*Какой будет результат выполнения программы?*
```
class s implements Runnable
{
    int x, y;
    public void run()
    {
        for(int i = 0; i < 1000; i++)
            synchronized(this)
            {
                x = 12;
                y = 12;
            }
        System.out.print(x + " " + y + " ");
    }
    public static void main(String args[])
    {
        s run = new s();
        Thread t1 = new Thread(run);
        Thread t2 = new Thread(run);
        t1.start();
        t2.start();
    }
}```'', 1, ''MEDIUM'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (68, ''bar'', ''bar done'', ''foo done'', ''Ошибка компиляции'', ''D'', ''Ошибка компиляции'', ''*Какой будет результат выполнения программы?*

```public class Test
{
    public static int y;
    public static void foo(int x)
    {
        System.out.print("foo ");
        y = x;
    }
    public static int bar(int z)
    {
        System.out.print("bar ");
        return y = z;
    }
    public static void main(String [] args )
    {
        int t = 0;
        assert t > 0 : bar(7);
        assert t > 1 : foo(8); /* Line 18 */
        System.out.println("done ");
    }
}```'', 1, ''MEDIUM'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (91, ''Decorator'', ''Proxy'', ''Factory Method'', ''Singleton'', ''D'', ''Singleton'', ''*К какому паттерну проектирования можно отнести данный фрагмент кода:*
 ```public enum Factory {
  PROXY
 } ```'', 1, ''MEDIUM'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (57, ''true true'', ''false false'', ''true false'', ''false true'', ''G'', ''true false'', ''*Какой будет результат выполнения программы?*
```Integer a = 120;
Integer b = 120;
Integer c = 130;
Integer d = 130;
System.out.println(a==b);
System.out.println(c==d);```'', 2, ''MEDIUM'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (58, ''x = 0'', ''x = 1'', ''Ошибка компиляции.'', ''Ошибка выполнения.'', ''C'', ''Ошибка компиляции.'', ''*Какой будет результат выполнения программы?*
```
class A
{
    final public int GetResult(int a, int b) { return 0; }
}
class B extends A
{
    public int GetResult(int a, int b) {return 1; }
}
public class Test
{
    public static void main(String args[])
    {
        B b = new B();
        System.out.println("x = " + b.GetResult(0, 1));
    }
}```'', 1, ''DIFFICULT'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (64, ''Он должен быть помечен final.'', ''Он может быть помечен abstract.'', ''Он может быть помечен public.'', ''Он может быть помечен static.'', ''B'', ''Он может быть помечен abstract.'', ''*Какое утверждение верно для локального внутреннего класса метода?*'', 1, ''DIFFICULT'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (67, ''Строка 5'', ''Строка 6'', ''Строка 12'', ''Строка 14'', ''D'', ''Строка 14'', ''*Какая строка вызовет ошибку компиляции?*
```
public class Test
{
    public void foo()
    {
        assert false; /* Line 5 */
        assert false; /* Line 6 */
    }
    public void bar()
    {
        while(true)
        {
            assert false; /* Line 12 */
        }
        assert false;  /* Line 14 */
    }
}```'', 1, ''DIFFICULT'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (61, ''1 и 2'', ''2 и 3'', ''3 и 4'', ''1 и 4'', ''C'', ''3 и 4'', ''*Какие два утверждения эквивалентны?*
 1. ```3/2```
 2. ```3<2```
 3. ```3*4```
 4. ```3<<2```'', 2, ''MEDIUM'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (59, ''9 7 7 foo 7 7foo'', ''72 34 34 foo34 34foo'', ''9 7 7 foo34 34foo'', ''72 7 34 foo34 7foo'', ''D'', ''72 7 34 foo34 7foo'', ''*Какой будет результат выполнения программы?*
```
class SC2
{
    public static void main(String [] args)
    {
        SC2 s = new SC2();
        s.start();
    }

    void start()
    {
        int a = 3;
        int b = 4;
        System.out.print(" " + 7 + 2 + " ");
        System.out.print(a + b);
        System.out.print(" " + a + b + " ");
        System.out.print(foo() + a + b + " ");
        System.out.println(a + b + foo());
    }

    String foo()
    {
        return "foo";
    }
}```'', 2, ''DIFFICULT'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (70, ''protected int a;'', ''transient int b = 3;'', ''private synchronized int e;'', ''volatile int d;'', ''C'', ''private synchronized int e;'', ''*Какое, из представленных, описание переменной на уровне класса не скомпилируется?*'', 2, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (71, ''i = 0'', ''i = 3'', ''i = 4'', ''Ошибка компиляции'', ''D'', ''Ошибка компиляции'', ''*Какой будет результат выполнения кода?*

``` int i = 0;
 while(1)
 {
  if(i == 4)
  {
   break;
  }
  ++i;
 }
 System.out.println("i = " + i);```'', 2, ''MEDIUM'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (73, ''I is 0'', ''I is 0 I is 1'', ''Ошибка компиляции'', ''Ни один из представленных'', ''C'', ''Ошибка компиляции'', ''*Какой будет результат выполнения кода?*

``` int I = 0;
 label:
  if (I < 2) {
  System.out.print("I is " + I);
  I++;
  continue label;
 }```'', 2, ''MEDIUM'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (75, ''apa'', ''app'', ''apea'', ''apep'', ''B'', ''app'', ''*Какой будет результат выполнения кода?*

``` String a = "newspaper";
 a = a.substring(5,7);
 char b = a.charAt(1);
 a = a + b;
 System.out.println(a);```'', 2, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (76, ''float f = 1F;'', ''float f = 1.0;'', ''float f = "1";'', ''float f = 1.0d;'', ''A'', ''float f = 1F;'', ''*Какой способ объявления float переменной допустим?*'', 2, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (77, ''1, 2 и 4'', ''2, 3 и 5'', ''3, 4 и 5'', ''1, 2 и 3'', ''B'', ''2, 3 и 5'', ''*Какие три утверждения верны?*

 1. Конструктор по умолчанию инициализирует переменные методов.
 2. Конструктор по умолчанию имеет такую же доступность, как и его класс.
 3. Конструктор по умолчанию вызывает конструктор без параметро в родительского класса.
 4. Если в классе отсвутствует конструктор без параметров, то компилятор создает конструктор по умолчанию.
 5. Компилятор создает конструктор по умолчанию только когда нет других конструкторов в классе.'', 2, ''MEDIUM'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (78, ''1 and 2'', ''2 and 3'', ''3 and 4'', ''Все строки корректны'', ''D'', ''Все строки корректны'', ''*Какие из представленных строк допустимы в коде?*

 1. ```int w = (int)888.8;```
 2. ```byte x = (byte)1000L;```
 3. ```long y = (byte)100;```
 4. ```byte z = (byte)100L;```'', 2, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (79, ''1 and 2'', ''2 and 4'', ''3 and 4'', ''1 and 3'', ''B'', ''2 and 4'', ''*Какие два выражения эквивалентны?*

 1. ```16*4```
 2. ```16>>2```
 3. ```16/2^2```
 4. ```16>>>2```'', 2, ''MEDIUM'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (81, ''1 и 4'', ''3 и 4'', ''1 и 3'', ''1 и 2'', ''С'', ''1 и 3'', ''*На какие два вида разделяется память в JVM?*

 1. Heap
 2. Queue
 3. Stack
 4. Persistence'', 1, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (82, ''От 0 до 255'', ''От –32768 до 32767'', ''От 0 до \uFFFF'', ''От 0 до 32767'', ''C'', ''От 0 до \uFFFF'', ''*В переменной типа char могут храниться значения из следующего диапазона:*'', 1, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (83, ''run()'', ''sleep(...)'', ''wait()'', ''yield()'', ''C'', ''wait()'', ''*Какой из следующих методов определен в классе Object?*'', 1, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (84, ''protected'', ''synchronized'', ''sealed'', ''volatile'', ''B'', ''synchronized'', ''*Какое ключевое слово используется, чтобы показать, что с методом может работать не более чем один поток одновременно?*'', 2, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (85, ''null всем ссылкам на объект'', ''Runtime.getRuntime().gc()'', ''метод finalize() у объекта'', ''этого нельзя сделать вручную'', ''D'', ''этого нельзя сделать вручную'', ''*Как можно уничтожить объект в Java?*'', 2, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (86, ''Да, можно'', ''Нет, нельзя'', '''', '''', ''B'', ''Нет, нельзя'', ''*Можно ли динамически менять размер массива?*'', 1, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (88, ''12'', ''01'', ''11'', ''10'', ''C'', ''11'', ''*Что выведет следующий код?*
 ```int i = 0;
 i++;
 System.out.print(i);
 i = i++;
 System.out.println(i); ```'', 1, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (89, ''2'', ''3'', ''2,3'', ''Код не содержит ошибок'', ''B'', ''3'', ''*В какой строке(-ах) кода содержится ошибка:*
 ```int i = 1;            //1
 i = -+(10 + 2 + i);   //2
 ++i--;                //3
 System.out.println(i); ```'', 1, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (90, ''Да'', ''Нет'', '''', '''', ''B'', ''Нет'', ''*Можно ли переопределяя метод изменить его модификатор доступа с "package-private" на "protected"?*'', 1, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (74, ''3.0'', ''-3.0'', ''NaN'', ''Ошибка компиляции'', ''C'', ''NaN'', ''*Какой будет результат выполнения кода?*

``` public class SqrtExample
 {
  public static void main(String [] args)
  {
   double value = -9.0;
   System.out.println( Math.sqrt(value));
  }
 }```'', 2, ''DIFFICULT'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (69, ''java'', ''javac'', ''javajavac'', ''Ошибка компиляции'', ''C'', ''javajavac'', ''*Какой будет результат выполнения программы?*

```public class Test138
{
    public static void stringReplace (String text)
    {
        text = text.replace (''''j'''' , ''''c''''); /* Line 5 */
    }
    public static void bufferReplace (StringBuffer text)
    {
        text = text.append ("c");  /* Line 9 */
    }
    public static void main (String args[])
    {
        String textString = new String ("java");
        StringBuffer textBuffer = new StringBuffer ("java"); /* Line 14 */
        stringReplace(textString);
        bufferReplace(textBuffer);
        System.out.println (textString + textBuffer);
    }
}```'', 2, ''MEDIUM'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (72, ''i = 1, j = 0'', ''i = 1, j = 4'', ''i = 3, j = 4'', ''Ошибка компиляции'', ''D'', ''Ошибка компиляции'', ''*Какой будет результат выполнения кода?*

```int i = 0, j = 5;
tp: for (;;)
    {
        i++;
        for (;;)
        {
            if(i > --j)
            {
                break tp;
            }
        }
        System.out.println("i =" + i + ", j = " + j);```'', 2, ''DIFFICULT'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (87, ''HashMap'', ''LinkedHashMap'', ''WeakHashMap'', ''IdentityHashMap'', ''B'', ''WeakHashMap'', ''*Из какой структуры данных "сборщик мусора" удалит все элементы у которых исчезла последняя ссылка на их ключ в этой структуре?*'', 2, ''DIFFICULT'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (92, ''Да'', ''Нет'', '''', '''', ''A'', ''Да'', ''*Данная реализация паттерна Singleton является thread safe:*
```public class Singleton {
   private Singleton() { }
   private static class SingletonHolder {
       private final static Singleton INSTANCE = new Singleton();
   }
   public static Singleton getInstance() {
       return SingletonHolder.INSTANCE;
   }
 } ```'', 1, ''MEDIUM'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (93, ''Добавить abstract'', ''Добавить final'', ''Добавить private'', '''', ''B'', ''Добавить final'', ''*Каким образом можно запретить наследование класса (речь идет о top-level классах)?*'', 1, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (94, ''Ошибка выполнения'', ''Ошибка компиляции'', ''Пустая строка'', '''', ''B'', ''Ошибка компиляции'', ''*Какой будет результат выполнения кода?*
```System.out.println(null);```'', 2, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (80, ''1'', ''2'', ''3'', ''4'', ''С'', ''3'', ''*Какое определение наиболее подходит к описанию JDK?*
1. Минимальная реализация виртуальной машины, необходимая для исполнения Java-приложений
2. Виртуальная машина Java.
3. Коммплект разработчика приложений на языке Java, включающий в себя компилятор Java, стандартные библиотеки классов Java
4. Ни одно не подходит.'', 2, ''EASY'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (60, ''count = 0'', ''count = 2'', ''count = 3'', ''count = 4'', ''C'', ''count = 3'', ''*Какой будет результат выполнения программы?*
```
class BoolArray
{
    boolean [] b = new boolean[3];
    int count = 0;

    void set(boolean [] x, int i)
    {
        x[i] = true;
        ++count;
    }

    public static void main(String [] args)
    {
        BoolArray ba = new BoolArray();
        ba.set(ba.b, 0);
        ba.set(ba.b, 2);
        ba.test();
    }

    void test()
    {
        if ( b[0] && b[1] | b[2] )
            count++;
        if ( b[1] && b[(++count - 2)] )
            count += 7;
        System.out.println("count = " + count);
    }
}```'', 1, ''DIFFICULT'');
INSERT INTO public.bot_question (id, answera, answerb, answerc, answerd, answerr, answerright, question, useday, weight) VALUES (95, ''java.util.SortedMap'', ''java.util.TreeMap'', ''java.util.TreeSet'', ''java.util.Hashtable'', ''D'', ''java.util.Hashtable'', ''*Какой класс коллекции позволяет вам получить доступ к своим элементам, связав ключ со значением элемента и обеспечив синхронизацию?*'', 2, ''DIFFICULT'');