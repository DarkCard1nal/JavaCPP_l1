package ReflectionExplore;

import java.lang.reflect.*;

public abstract class Task3 {

	public static void main(String[] args) {
		String exampleString = "Hello, World!";
		char c1 = 'l';
		char c2 = '2';
		System.out.println("String: " + exampleString);

		try {
			System.out.println(callMethod(exampleString, "length"));
			System.out.println(callMethod(exampleString, "replace", c1, c2));
			System.out.println(callMethod(exampleString, "nonExistentMethod"));
		} catch (FunctionNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static String callMethod(Object obj, String methodName, Object... args) throws FunctionNotFoundException {
		Class<?> objClass = obj.getClass();

		try {
			Method method = objClass.getMethod(methodName, getParameterTypes(args));
			Object result = method.invoke(obj, args);
			return ("Результат виклику методу " + methodName + ": " + result);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			throw new FunctionNotFoundException("Method " + methodName + " not found");
		}
	}

	private static Class<?>[] getParameterTypes(Object[] args) {
		Class<?>[] parameterTypes = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			Class<?> argClass = args[i].getClass();
			if (argClass == Integer.class) {
				parameterTypes[i] = int.class;
			} else if (argClass == Boolean.class) {
				parameterTypes[i] = boolean.class;
			} else if (argClass == Character.class) {
				parameterTypes[i] = char.class;
			} else if (argClass == Byte.class) {
				parameterTypes[i] = byte.class;
			} else if (argClass == Short.class) {
				parameterTypes[i] = short.class;
			} else if (argClass == Long.class) {
				parameterTypes[i] = long.class;
			} else if (argClass == Float.class) {
				parameterTypes[i] = float.class;
			} else if (argClass == Double.class) {
				parameterTypes[i] = double.class;
			} else {
				parameterTypes[i] = argClass;
			}
		}
		return parameterTypes;
	}

}

class FunctionNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FunctionNotFoundException(String message) {
		super(message);
	}

	public FunctionNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}