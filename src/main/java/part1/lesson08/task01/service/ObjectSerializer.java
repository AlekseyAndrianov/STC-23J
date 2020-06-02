package part1.lesson08.task01.service;

import sun.reflect.ReflectionFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * void serialize (Object object, String file);
 * Object deSerialize(String file);
 * Методы выполняют сериализацию объекта Object в файл file и десериализацию объекта из этого файла.
 */
public class ObjectSerializer {

    public void serialize(Object object, String file) {

        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                bufferedWriter.write(field.getName());
                bufferedWriter.write(":");
                bufferedWriter.write(field.get(object).toString());
                bufferedWriter.newLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Object deSerialize(String file) {
        Object object = null;
//        try (FileReader fileReader = new FileReader(file);
//             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
//
//            ReflectionFactory.getReflectionFactory().newFieldAccessor();
//           bufferedReader.lines().peek(line -> {
//               String[] field = line.split(":");
//               modifiers:classname:name:value
//               try {
//                   Field f = new Field(Class.forName(field[1]), field[2], Class.forName(field[1]), field[0]);
//               } catch (ClassNotFoundException e) {
//                   e.printStackTrace();
//               }
//           });
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return object;
    }

}
