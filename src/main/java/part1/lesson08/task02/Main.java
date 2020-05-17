package part1.lesson08.task02;


import part1.lesson08.task02.entities.AnotherObject;
import part1.lesson08.task02.entities.SomeObject;
import part1.lesson08.task02.service.ObjectSerializer;

/**
 * Задание 2. Предусмотреть работу c любыми типами полей (полями могут быть ссылочные типы).
 * <p>
 * Требование: Использовать готовые реализации (Jaxb, jackson и т.д.) запрещается.
 */
public class Main {

    private static final String file = "C:\\Users\\Admin\\Desktop\\destination.txt";

    public static void main(String[] args) {

        SomeObject someObject = SomeObject
                .builder()
                .ind(1000L)
                .name("Example")
                .size(10)
                .weight(25.0)
                .build();

        AnotherObject anotherObject = AnotherObject
                .builder()
                .name("Another example")
                .someObject(someObject)
                .locationName("Moscow")
                .size(45)
                .weight(65.3)
                .build();

        ObjectSerializer objectSerializer = new ObjectSerializer();
        objectSerializer.serialize(anotherObject, file);
        System.out.println("anotherObject: " + anotherObject);

        AnotherObject deSerializedObject = (AnotherObject) objectSerializer.deSerialize(file);
        System.out.println("deSerializedObject: " + deSerializedObject);
    }

}
