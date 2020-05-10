package part1.lesson03.task03;

import part1.lesson03.task01.MathBox;
import part1.lesson03.task02.ObjectBox;

/**
 * Задание 3. Доработать классы MathBox и ObjectBox таким образом, чтобы MathBox был наследником ObjectBox.
 * Необходимо сделать такую связь, правильно распределить поля и методы. Функциональность в целом должна сохраниться.
 * При попытке положить Object в MathBox должно создаваться исключение.
 */
public class Main {
    public static void main(String[] args) {
        ObjectBox mathBox = new MathBox(new Number[]{10, 11, 12.2, 13, 4324});
        mathBox.addObject(134234);
//        objectBox.addObject("sdgs");
        mathBox.addObject(13.0);

        mathBox.deleteObject(11);

        mathBox.dump();
        System.out.println();

        ((MathBox)mathBox).splitter(2);
        System.out.println("Summator result after splitter: " + ((MathBox)mathBox).summator());

        System.out.println(mathBox.toString());

        ((MathBox)mathBox).removeIntegerIfExist(5);
        System.out.println("Summator result after remove: " + ((MathBox)mathBox).summator());

        System.out.println(mathBox.toString());
    }
}
