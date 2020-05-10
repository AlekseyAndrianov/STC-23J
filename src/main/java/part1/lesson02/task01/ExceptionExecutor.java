package part1.lesson02.task01;

/**
 * Задание 1. Написать программу ”Hello, World!”. В ходе выполнения программы она должна выбросить исключение и завершиться с ошибкой.
 *
 * Смоделировав ошибку «NullPointerException»
 * Смоделировав ошибку «ArrayIndexOutOfBoundsException»
 * Вызвав свой вариант ошибки через оператор throw
 */
public class ExceptionExecutor {

    public static void main(String[] args) {

        ExceptionScenariosHandler.nullPointerExceptionCase(null);

    }
}
