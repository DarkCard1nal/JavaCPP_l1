package ReflectionExplore;

import java.lang.reflect.*;
import java.util.Scanner;

public abstract class Task2 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String exampleString = "Hello, World!";
		//String exampleMethod = "length";
		System.out.println(inspect(exampleString));
		
		System.out.println("Введіть назву відкритого метода без параметрів для його виконання над об'єктом"
				+ " (наприклад toString): ");
		
		String exampleMethod = scan.nextLine();
		
		System.out.println(invokeMethod(exampleString, exampleMethod));
		scan.close();
	}
	
	public static String inspect(Object obj) {
		// Клас об'єкта
		Class<?> objClass = obj.getClass();
		StringBuilder description = new StringBuilder();

		// Тип об'єкта
		description.append("Type: " + objClass.getName() + "\n");

		// Стан об'єкта (значення полів)
		description.append("State:" + "\n");
		Field[] fields = objClass.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true); // Доступ до приватних полів
				description.append(field.getName() + ": " + field.get(obj) + "\n");
			} catch (IllegalAccessException | java.lang.reflect.InaccessibleObjectException e) {
				e.printStackTrace();
			}
		}

		// Виводимо список відкритих методів
		description.append("Public methods:" + "\n");
		Method[] methods = objClass.getMethods();
		int n = 0;
		for (Method method : methods) {
			description.append(n++ + ") ");
			String methodName = method.getName();
			String returnType = method.getReturnType().getSimpleName();
			Parameter[] parameters = method.getParameters();
			StringBuilder methodSignature = new StringBuilder();
			methodSignature.append(returnType).append(" ").append(methodName).append(" (");
			for (int i = 0; i < parameters.length; i++) {
				Parameter parameter = parameters[i];
				String parameterType = parameter.getType().getSimpleName();
				methodSignature.append(parameterType).append(" ").append(parameter.getName());
				if (i < parameters.length - 1) {
					methodSignature.append(", ");
				}
			}
			methodSignature.append(")");
			description.append(methodSignature + "\n");
		}

		return description.toString();
	}

	// Метод викликає відкритий метод без параметрів з назвою methodName на об'єкті obj, повертає результати
	public static String invokeMethod(Object obj, String methodName) {
		Class<?> objClass = obj.getClass();
		StringBuilder description = new StringBuilder();

		try {
			Method method = objClass.getMethod(methodName);
			Object result = method.invoke(obj);
			description.append("Метод знайдено та виконано над об'єктом, результат його виконання: "
					+ result + "\n" );
			description.append(inspect(obj));
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			description.append("Метод з назвою '" + methodName + "' не знайдено, або він не відповідає вимогам.\n");
			e.printStackTrace();
		}

		return description.toString();
	}
}
