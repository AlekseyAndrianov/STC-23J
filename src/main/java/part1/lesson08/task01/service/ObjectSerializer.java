package part1.lesson08.task01.service;

import part1.lesson08.task01.entities.SomeObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * void serialize (Object object, String file);
 * Object deSerialize(String file);
 * Методы выполняют сериализацию объекта Object в файл file и десериализацию объекта из этого файла.
 */
public class ObjectSerializer {

    private static int countObjects;
    private static List<String> tree = new ArrayList<>();

    public String serialize(Object object, String file) throws IllegalAccessException {

        String objectAsString = objectToString(object);
        System.out.println("Serialized: " + objectAsString);
        return objectAsString;
//        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
//             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
//
//            objectOutputStream.writeObject(object);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    private String getValue(Object object, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        Class type = field.getType();
        if (type.isPrimitive() || type.equals(String.class)) {
            return field.get(object).toString();
        }
        return objectToString(field.get(object));
    }

    private String objectToString(Object object) throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();
        Field[] fields = object.getClass().getDeclaredFields();
        stringBuilder.append("{");
        Iterator<Field> iterator = Arrays.asList(fields).iterator();
        while (iterator.hasNext()){
            Field field = iterator.next();

            String fieldAsString = String.format("%s:%s", field.getName(), getValue(object, field));
            stringBuilder.append(fieldAsString);
            if (iterator.hasNext())
                stringBuilder.append(",");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

}
