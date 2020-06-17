package part1.lesson08.task01;


import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import part1.lesson08.task01.entities.AnotherPojoObject;
import part1.lesson08.task01.entities.SomeObject;
import part1.lesson08.task01.service.ObjectDeSerializer;
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
    private static final String file = "C:\\Users\\Admin\\Desktop\\destination.txt";

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
        AnotherPojoObject anotherPojoObject = AnotherPojoObject.builder()
                .id(1L)
                .length(123.0)
                .name("sdfsf00")
                .size(56)
                .weight(656.3)
                .build();

        SomeObject someObject = SomeObject.builder()
                .ind(1000L)
                .name("Example")
                .size(10)
                .weight(25.0)
                .anotherPojoObject(anotherPojoObject)
                .build();

        ObjectSerializer objectSerializer = new ObjectSerializer();
        String serializing = objectSerializer.serialize(someObject, file);

        ObjectDeSerializer objectDeSerializer = new ObjectDeSerializer();
        objectDeSerializer.executeDeSerializing(serializing);
//        System.out.println("someObject: " + someObject);
//
//        SomeObject deSerializedObject = (SomeObject) objectSerializer.deSerialize(file);
//        System.out.println("deSerializedObject: " + deSerializedObject);
    }

}
