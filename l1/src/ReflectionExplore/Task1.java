package ReflectionExplore;

import java.lang.reflect.*;
import java.util.Scanner;

public abstract class Task1 {
	
	public static void main(String[] args) throws ClassNotFoundException {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Type Java class full name (for example java.lang.String[][]):");
		
		String className = scan.nextLine();
		
		System.out.println("\n" + getClassDescription(className));
		
		scan.close();
	}

	public static String getClassDescription(String className) throws ClassNotFoundException {
		className = className.trim();

		if (className.endsWith("[]")) {
			// Масив
			int dimensions = 0;
			while (className.endsWith("[]")) {
				dimensions++;
				className = className.substring(0, className.length() - 2);
			}
			return "Is an array with the number of dimensions: " + dimensions + "\n" 
				+ getClassDescription(className);
		} else if (className.startsWith("[")) {
			// Примітивний тип масиву
			return className;
		} else {
			// Клас або інтерфейс
			Class<?> classOb = Class.forName(className);
			return getClassDescription(classOb);
		}
	}

	public static String getClassDescription(Class<?> classOb) {
		StringBuilder description = new StringBuilder();

		// Ім'я пакета
		Package pkg = classOb.getPackage();
		if (pkg != null) {
			description.append("package ").append(pkg.getName()).append(", Java Platform API Specification, version ")
					.append(System.getProperty("java.specification.version")).append("\n\n");
		}

		// Ім'я класу
		description.append("public ");
		if (Modifier.isFinal(classOb.getModifiers())) {
			description.append("final ");
		}
		description.append("class ").append(classOb.getSimpleName());

		// Інтерфейси
		Class<?>[] interfaces = classOb.getInterfaces();
		if (interfaces.length > 0) {
			description.append(" implements ");
			for (int i = 0; i < interfaces.length; i++) {
				description.append(interfaces[i].getSimpleName());
				if (i < interfaces.length - 1) {
					description.append(", ");
				}
			}
		}

		description.append(" {\n");

		// Поля
		Field[] fields = classOb.getDeclaredFields();
		if (fields.length > 0) {
			description.append("\t// Поля\n");
			for (Field field : fields) {
				description.append("\t");
				description.append(Modifier.toString(field.getModifiers())).append(" ")
						.append(field.getType().getSimpleName()).append(" ").append(field.getName()).append(";\n");
			}
		}

		// Конструктори
		Constructor<?>[] constructors = classOb.getDeclaredConstructors();
		if (constructors.length > 0) {
			description.append("\n\t// Конструктори\n");
			for (Constructor<?> constructor : constructors) {
				description.append("\t");
				description.append(Modifier.toString(constructor.getModifiers())).append(" ")
						.append(classOb.getSimpleName()).append("(");
				Class<?>[] parameterTypes = constructor.getParameterTypes();
				for (int i = 0; i < parameterTypes.length; i++) {
					description.append(parameterTypes[i].getSimpleName()).append(" par").append(i);
					if (i < parameterTypes.length - 1) {
						description.append(", ");
					}
				}
				description.append(");\n");
			}
		}

		// Методи
		Method[] methods = classOb.getDeclaredMethods();
		if (methods.length > 0) {
			description.append("\n\t// Методи\n");
			for (Method method : methods) {
				description.append("\t");
				description.append(Modifier.toString(method.getModifiers())).append(" ")
						.append(method.getReturnType().getSimpleName()).append(" ").append(method.getName())
						.append("(");
				Class<?>[] parameterTypes = method.getParameterTypes();
				for (int i = 0; i < parameterTypes.length; i++) {
					description.append(parameterTypes[i].getSimpleName()).append(" par").append(i);
					if (i < parameterTypes.length - 1) {
						description.append(", ");
					}
				}
				description.append(");\n");
			}
		}

		description.append("}\n");

		return description.toString();
	}
}
