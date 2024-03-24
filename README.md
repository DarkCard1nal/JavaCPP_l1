# JavaCPP_l1
_Created for the course "Cross-platform programming" V. N. Karazin Kharkiv National University_

Java 11 set of methods for testing the Java Reflection API

The source codes of the classes can be found in _l1\src\ReflectionExplore\_

___

# Task 1

In the Task1.java method: 

`public static String getClassDescription(String className)`

`public static String getClassDescription(Class<?> classOb)`

`getClassDescription` returns a string with its complete description: the name of the package in which the class is defined, modifiers and the name of the analyzed class, its base class, a list of implemented interfaces, and a list of all fields, constructors, and methods declared in the class and their characteristics.

Task1UI.java implements a graphical interface for the getClassDescription method of the Task1 class using `Swing` and `AWT`.

## Example:
```java
Type Java class full name (for example java.lang.String[][]):
java.lang.String[][]

Is an array with the number of dimensions: 2
package java.lang, Java Platform API Specification, version 20

public final class String implements Serializable, Comparable, CharSequence, Constable, ConstantDesc {
	// Поля
	private final byte[] value;
	private final byte coder;
	...

	// Конструктори
	public String(StringBuilder par0);
	 String(char[] par0, int par1, int par2, Void par3);
	 String(AbstractStringBuilder par0, Void par1);
	private String(Charset par0, byte[] par1, int par2, int par3);
	public String(byte[] par0, int par1, int par2, Charset par3);
	public String(byte[] par0, String par1);
	...

	// Методи
	 byte[] value();
	public boolean equals(Object par0);
	public int length();
	...
```
![image](https://github.com/DarkCard1nal/JavaCPP_l1/assets/34416583/fb2a22cf-1b12-479f-b9a5-0e815ba69427)

# Task 2

In Task2.java, methods are implemented:

`public static String inspect(Object obj)`

`inspect` - returns a string with its real type and state - a list of all fields declared in the class, along with their values, as well as a list of public methods declared in the class.

`public static String invokeMethod(Object obj, String methodName)`

`invokeMethod` - calls an open method without parameters named methodName on the obj object, returns the results of its execution, or an error if such a method without parameters is not found.

The errors `"java.lang.reflect.InaccessibleObjectException: Unable to make field private final byte[] java.lang.String.value accessible: module java.base does not "open java.lang" to module l1"` and similar errors when executing the invokeMethod method are related to the Java platform module system that was introduced in Java 9, in particular its implementation of strong encapsulation. It allows access only under certain conditions, and does not allow reflection to setAccessible for JDK modules, making them immutable. More information and solutions are available here: 
https://stackoverflow.com/questions/41265266/how-to-solve-inaccessibleobjectexception-unable-to-make-member-accessible-m

## Example:
```java
>> String exampleString = "Hello, World!"
>> inspect(exampleString)

// all fields declared in the class

Type: java.lang.String
State:
CASE_INSENSITIVE_ORDER: java.lang.String$CaseInsensitiveComparator@2ef1e4fa
Public methods:
0) boolean equals (Object arg0)
1) int length ()
2) String toString ()
...

>> invokeMethod(exampleString, length)

Метод знайдено та виконано над об'єктом, результат його виконання: 13
// inspect(exampleString)
```

# Task 3

In Task3.java methods:

`public static String callMethod(Object obj, String methodName, Object... args)`

`callMethod` - calls the method methodName on obj with parameters args, returns the results of its execution, or a FunctionNotFoundException.

## Example:
```java
>> String exampleString = "Hello, World!"
>> char c1 = 'l'
>> char c2 = '2'

>> callMethod(exampleString, "length")

Результат виклику методу length: 13

>> callMethod(exampleString, "replace", c1, c2)

Результат виклику методу replace: He22o, Wor2d!

>> callMethod(exampleString, "nonExistentMethod") // no such method exists

ReflectionExplore.FunctionNotFoundException: Method nonExistentMethod not found
	at l1/ReflectionExplore.Task3.callMethod(Task3.java:31)
	at l1/ReflectionExplore.Task3.main(Task3.java:16)
```

# Task 4

In Task4.java methods:

`public static <T> T[] createArray(Class<T> type, int size)`

`createArray` - creates and returns a one-dimensional array of `type` `size`.

`public static <T> T[] resizeArray(T[] array, int newSize)`

`resizeArray` - returns a one-dimensional array of `array` with the resized size `newSize`.

`public static <T> String toString(T[] array)`

`toString` - converts and returns a one-dimensional `array` to a string.

`public static <T> T[][] createMatrix(Class<T> type, int rows, int cols)`

`createMatrix` - creates and returns a matrix (two-dimensional array) of type with `rows` - the number of rows, `cols` - the number of columns.

`public static <T> T[][] resizeMatrix(T[][] matrix, int newRows, int newCols)`

`resizeMatrix` - returns a `matrix` with the changed number of rows `newRows` and columns `newCols`.

`public static <T> String toString(T[][] matrix)`

`toString` - converts and returns a `matrix` to a string.

`public static <T> void printMatrix(T[][] matrix)`

`printMatrix` - prints a `matrix` to the console.

## Example:
```java
>> Integer[] array = createArray(Integer.class, 5)

[null, null, null, null, null]

>> array = resizeArray(array, 8)

[null, null, null, null, null, null, null, null]

>> toString(array)

{[null, null, null, null, null, null, null, null]}

>> Integer[][] matrix = createMatrix(Integer.class, 3, 3)
>> printMatrix(matrix)

[null, null, null]
[null, null, null]
[null, null, null]

>> matrix = resizeMatrix(matrix, 4, 4)
>> printMatrix(matrix)

[null, null, null, null]
[null, null, null, null]
[null, null, null, null]
[null, null, null, null]

>> toString(matrix)

{[null, null, null, null], [null, null, null, null], [null, null, null, null], [null, null, null, null]}
```

# Task 5

In the Task5.java:

`class Task5` - testing the program.

`class Function1`, `Function2` - mathematical functions.

`class MethodProfiler implements InvocationHandler` creates and uses proxy objects, profiles the execution time of methods. The `invoke` method is called every time the method of the object that is represented by the proxy is called. 

## Example:
```java
>>
  Evaluatable f1 = new Function1();
  Evaluatable f2 = new Function2();

  Evaluatable proxyF1 = (Evaluatable) Proxy.newProxyInstance(Task5.class.getClassLoader(),
      new Class[] { Evaluatable.class }, new MethodProfiler(f1));

  Evaluatable proxyF2 = (Evaluatable) Proxy.newProxyInstance(Task5.class.getClassLoader(),
      new Class[] { Evaluatable.class }, new MethodProfiler(f2));

  double x = 1.0;

  System.out.println("F1: " + proxyF1.evalf(x));
  System.out.println("F2: " + proxyF2.evalf(x));


[evalf] took 7627800 ns
[evalf](1.0) = 0.06907214463000695
F1: 0.06907214463000695
[evalf] took 19300 ns
[evalf](1.0) = 1.0
F2: 1.0
```

# Task 6 (bonus 1)

Task6.java is a console program that allows you to view the list of constructors of a class specified during program operation, select the desired constructor and create an object of this class, then view the list of all class methods and select the desired method. The program uses the code from previous tasks (the `java.lang.reflect.InaccessibleObjectException` error is described in `Task 2`).

## Example:
```java
Type Java class full name (for example java.lang.String):

>> java.lang.String

Конструктори:
0) public String (StringBuilder);
1) public String (char[], int, int, Void);
...
13) public String (String);
14) public String ();
...
Оберіть конструктор, ввівши його номер

>> 13

Введіть значення параметрів через пробіл:

>> Hello

Об'єкт класу String створено.
... // Task2.inspect(instance)
Public methods:
0) boolean equals (Object arg0)
1) int length ()
...
43) String replace (char arg0, char arg1)
...
Оберіть метод, ввівши його номер:

>> 43

Введіть значення параметрів через пробіл:

>> l 2

Результат виклику методу: He22o
... // Task2.inspect(instance)
```
