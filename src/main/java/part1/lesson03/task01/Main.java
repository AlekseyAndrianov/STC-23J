package part1.lesson03.task01;

/**
 * Задание 1. Написать класс MathBox, реализующий следующий функционал:
 *
 *
 */
public class Main {

    public static void main(String[] args) {

        MathBox mathBox = new MathBox(new Number[]{10, 11, 12.2, 13, 4324});
        System.out.println("Summator result: " + mathBox.summator());

        mathBox.splitter(2);
        System.out.println("Summator result after splitter: " + mathBox.summator());

        System.out.println(mathBox.toString());

        mathBox.removeIntegerIfExist(5);
        System.out.println("Summator result after remove: " + mathBox.summator());

        System.out.println(mathBox.toString());

    }
}
