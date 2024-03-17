package ReflectionExplore;

import java.lang.reflect.*;
import java.util.Scanner;

public abstract class Task6 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Class<?> classOb;
		Class<?>[] parameterTypes;
		Object instance;
		Object[] parameters;
		String line;
		int n;

		System.out.println("Type Java class full name (for example java.lang.String):");

		line = scan.nextLine();

		try {
			classOb = Class.forName(line.trim());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			scan.close();
			return;
		}

		Constructor<?>[] constructors = classOb.getDeclaredConstructors();
		if (constructors.length > 0) {
			System.out.println("Конструктори:");
			n = 0;
			for (Constructor<?> constructor : constructors) {
				StringBuilder constructorSignature = new StringBuilder();
				constructorSignature.append(n + ") " + "public ").append(classOb.getSimpleName()).append(" (");
				parameterTypes = constructor.getParameterTypes();
				for (int i = 0; i < parameterTypes.length; i++) {
					constructorSignature.append(parameterTypes[i].getSimpleName());
					if (i < parameterTypes.length - 1) {
						constructorSignature.append(", ");
					}
				}
				constructorSignature.append(");");
				System.out.println(constructorSignature);
				n++;
			}
		} else {
			System.out.println("Конструктори не знайдено");
			scan.close();
			return;
		}

		System.out.println("Оберіть конструктор, ввівши його номер");

		n = scan.nextInt();
		if (n < 0 || n > constructors.length) {
			System.out.println("Конструктор не знайдено");
			scan.close();
			return;
		}

		parameterTypes = constructors[n].getParameterTypes();

		parameters = new Object[parameterTypes.length];

		System.out.println("Введіть значення параметрів через пробіл:");

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i] == byte.class || parameterTypes[i] == Byte.class) {
				parameters[i] = scan.nextByte();
			} else if (parameterTypes[i] == short.class || parameterTypes[i] == Short.class) {
				parameters[i] = scan.nextShort();
			} else if (parameterTypes[i] == int.class || parameterTypes[i] == Integer.class) {
				parameters[i] = scan.nextInt();
			} else if (parameterTypes[i] == long.class || parameterTypes[i] == Long.class) {
				parameters[i] = scan.nextLong();
			} else if (parameterTypes[i] == float.class || parameterTypes[i] == Float.class) {
				parameters[i] = scan.nextFloat();
			} else if (parameterTypes[i] == double.class || parameterTypes[i] == Double.class) {
				parameters[i] = scan.nextDouble();
			} else if (parameterTypes[i] == char.class || parameterTypes[i] == Character.class) {
				parameters[i] = scan.next().charAt(0);
			} else if (parameterTypes[i] == boolean.class || parameterTypes[i] == Boolean.class) {
				parameters[i] = scan.nextBoolean();
			} else if (parameterTypes[i] == String.class) {
				parameters[i] = scan.next();
			}
			else {
				System.out.println("Нажаль, в цьому конструкторі наявний не примітивний тип, " 
						+ "створення об'єкту неможливе.");
				scan.close();
				return;
			}
		}
		
		try {
			instance = constructors[n].newInstance(parameters);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			scan.close();
			return;
		}
		
		System.out.println("Об'єкт класу " + classOb.getSimpleName() + " створено.");
		
		System.out.println(Task2.inspect(instance));
		
		Method[] methods = classOb.getMethods();
		
		System.out.println("Оберіть метод, ввівши його номер:");
		n = scan.nextInt();
		if (n < 0 || n > methods.length) {
			System.out.println("Метод не знайдено");
			scan.close();
			return;
		}
		
		parameterTypes = methods[n].getParameterTypes();

		parameters = new Object[parameterTypes.length];

		System.out.println("Введіть значення параметрів через пробіл:");

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i] == byte.class || parameterTypes[i] == Byte.class) {
				parameters[i] = scan.nextByte();
			} else if (parameterTypes[i] == short.class || parameterTypes[i] == Short.class) {
				parameters[i] = scan.nextShort();
			} else if (parameterTypes[i] == int.class || parameterTypes[i] == Integer.class) {
				parameters[i] = scan.nextInt();
			} else if (parameterTypes[i] == long.class || parameterTypes[i] == Long.class) {
				parameters[i] = scan.nextLong();
			} else if (parameterTypes[i] == float.class || parameterTypes[i] == Float.class) {
				parameters[i] = scan.nextFloat();
			} else if (parameterTypes[i] == double.class || parameterTypes[i] == Double.class) {
				parameters[i] = scan.nextDouble();
			} else if (parameterTypes[i] == char.class || parameterTypes[i] == Character.class) {
				parameters[i] = scan.next().charAt(0);
			} else if (parameterTypes[i] == boolean.class || parameterTypes[i] == Boolean.class) {
				parameters[i] = scan.nextBoolean();
			} else if (parameterTypes[i] == String.class) {
				parameters[i] = scan.next();
			}
			else {
				System.out.println("Нажаль, в цьому методі наявний не примітивний тип, " 
						+ "його виконання неможливе.");
				scan.close();
				return;
			}
		}
		
		Object result = null;
		try {
			result = methods[n].invoke(instance, parameters);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		System.out.println("Результат виклику методу: " + result);
		
		System.out.println(Task2.inspect(instance));
		
		scan.close();
	}

}
