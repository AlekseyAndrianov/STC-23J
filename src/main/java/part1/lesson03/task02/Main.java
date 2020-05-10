package part1.lesson03.task02;

/**
 * Задание 2. Создать класс ObjectBox, который будет хранить коллекцию Object.
 * У класса должен быть метод addObject, добавляющий объект в коллекцию.
 * У класса должен быть метод deleteObject, проверяющий наличие объекта в коллекции и при наличии удаляющий его.
 * Должен быть метод dump, выводящий содержимое коллекции в строку.
 */
public class Main {

    public static void main(String[] args) {
        ObjectBox objectBox = new ObjectBox();
        objectBox.addObject(134234);
        objectBox.addObject("sdgs");
        objectBox.addObject(13.0);

        objectBox.deleteObject(134234);

        objectBox.dump();
    }
}
