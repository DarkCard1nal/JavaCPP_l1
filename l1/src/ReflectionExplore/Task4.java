package ReflectionExplore;

import java.lang.reflect.*;
import java.util.Arrays;

public abstract class Task4 {
	public static void main(String[] args) {
		// Приклад роботи з одновимірним масивом
		Integer[] array = createArray(Integer.class, 5);
		System.out.println("Початковий масив: " + Arrays.toString(array));

		array = resizeArray(array, 8);
		System.out.println("Масив після зміни розміру: " + Arrays.toString(array));

		// Перетворення масиву в рядок
		String arrayAsString = toString(array);
		System.out.println("Масив як рядок: " + arrayAsString);

		// Приклад роботи з матрицею
		Integer[][] matrix = createMatrix(Integer.class, 3, 3);
		System.out.println("Початкова матриця:");
		printMatrix(matrix);

		matrix = resizeMatrix(matrix, 4, 4);
		System.out.println("Матриця після зміни розміру:");
		printMatrix(matrix);

		// Перетворення матриці в рядок
		String matrixAsString = toString(matrix);
		System.out.println("Матриця як рядок: " + matrixAsString);
	}

	// Створення одновимірного масиву заданого типу та розміру
	public static <T> T[] createArray(Class<T> type, int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) Array.newInstance(type, size);
		return array;
	}

	// Зміна розміру одновимірного масиву
	public static <T> T[] resizeArray(T[] array, int newSize) {
		return Arrays.copyOf(array, newSize);
	}

	// Перетворення одновимірного масиву в рядок
	public static <T> String toString(T[] array) {
		return "{" + Arrays.toString(array) + "}";
	}

	// Створення матриці заданого типу та розмірів
	public static <T> T[][] createMatrix(Class<T> type, int rows, int cols) {
		@SuppressWarnings("unchecked")
		T[][] matrix = (T[][]) Array.newInstance(type, rows, cols);
		return matrix;
	}

	// Зміна розміру матриці
	@SuppressWarnings("unchecked")
	public static <T> T[][] resizeMatrix(T[][] matrix, int newRows, int newCols) {
		matrix = Arrays.copyOf(matrix, newRows);
		for (int i = 0; i < newRows; i++) {
			if (matrix[i] != null)
				matrix[i] = Arrays.copyOf(matrix[i], newCols);
			else
				matrix[i] = (T[]) Array.newInstance(matrix.getClass().getComponentType().getComponentType(), newCols);
		}
		return matrix;
	}

	// Перетворення матриці в рядок
	public static <T> String toString(T[][] matrix) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (T[] row : matrix) {
			sb.append(Arrays.toString(row) + ", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append("}");
		return sb.toString();
	}

	// Друкує матрицю в консоль
	public static <T> void printMatrix(T[][] matrix) {
		for (T[] row : matrix) {
			System.out.println(Arrays.toString(row));
		}
	}
}
