package part1.lesson08.task01;


import part1.lesson08.task01.entities.SomeObject;
import part1.lesson08.task01.service.ObjectSerializer;

/**
 * Задание 1. Необходимо разработать класс, реализующий следующие методы:
 * <p>
 * void serialize (Object object, String file);
 * Object deSerialize(String file);
 * Методы выполняют сериализацию объекта Object в файл file и десериализацию объекта из этого файла.
 * Обязательна сериализация и десериализация "плоских" объектов (все поля объекта - примитивы, или String).
 */
public class Main {

    private static final String file = "D:\\destination.txt";

    public static void main(String[] args) {

        SomeObject someObject = SomeObject.builder()
                .ind(1000L)
                .name("Example")
                .size(10)
                .weight(25.0)
                .build();

        ObjectSerializer objectSerializer = new ObjectSerializer();
        objectSerializer.serialize(someObject, file);
//        System.out.println("someObject: " + someObject);
//
//        SomeObject deSerializedObject = (SomeObject) objectSerializer.deSerialize(file);
//        System.out.println("deSerializedObject: " + deSerializedObject);
    }

}
