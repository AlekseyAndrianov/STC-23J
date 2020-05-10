package part1.lesson02.task01;

public class HelloWorldException extends IllegalArgumentException {

    public HelloWorldException(String message) {
        super(String.format("'%s' is incorrect argument!", message));
    }
}
