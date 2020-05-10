package part1.lesson02.task01;

/**
 * Class with 3 static methods for simulation exception.
 */
public class ExceptionScenariosHandler {

    /**
     * Моделирование ошибки «NullPointerException»
     *
     * @param message must be null for exception
     */
    public static void nullPointerExceptionCase(String message) {
        if ("Hello, World!".length() == message.length())
            System.out.println("Hello, World!");
    }

    /**
     * Моделирование ошибки ArrayIndexOutOfBoundsException
     */
    public static void arrayIndexOutOfBoundsExceptionCase() {
        String message = "Hello, World!";
        System.out.println(String.format("Char for last index of string '%s' is '%s'", message, message.charAt(message.length())));
    }


    /**
     * Моделирование своего варианта ошибки через оператор throw
     *
     * @param message
     */
    public static void throwExceptionCase(String message) {
        if (message.length() < 13)
            throw new HelloWorldException(message);
        System.out.println(message);
    }
}
