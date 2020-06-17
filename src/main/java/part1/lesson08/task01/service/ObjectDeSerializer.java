package part1.lesson08.task01.service;

import part1.lesson08.task01.entities.SomeObject;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectDeSerializer {


    public Object executeDeSerializing(String objectAsString) throws ClassNotFoundException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Object o = deSerialize(objectAsString, SomeObject.class);
        SomeObject someObject = (SomeObject) o;
        System.out.println("someObject: " + someObject);
        return someObject;
    }

    // При вызове нужно передать строку соответствующую объекту
    public Object deSerialize(String objectAsString, Class<?> someObjectClass) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        System.out.println("objectAsString: " + objectAsString);
// Разбиваем объект на поля
        Map<String, String> objectFields = getObjectPairs(objectAsString);
        objectFields.entrySet().forEach(System.out::println);

        // Создаем объект класса
        Object o = someObjectClass.newInstance();
        for (Map.Entry<String, String> entry : objectFields.entrySet()) {


            Field field = o.getClass().getDeclaredField(entry.getKey());
            field.setAccessible(true);
            Class<?> cl = field.getType();
            if (field.getName().equals("serialVersionUID")) {
                if ((long) field.get(o) == Long.parseLong(entry.getValue())) {
                } else
                    throw new NoSuchFieldException();
            } else if (cl.equals(short.class)) {
                field.set(o, Short.parseShort(entry.getValue()));
            } else if (cl.equals(byte.class)) {
                field.set(o, Byte.parseByte(entry.getValue()));
            } else if (cl.equals(int.class)) {
                field.set(o, Integer.parseInt(entry.getValue()));
            } else if (cl.equals(long.class)) {
                field.set(o, Long.parseLong(entry.getValue()));
            } else if (cl.equals(double.class)) {
                field.set(o, Double.parseDouble(entry.getValue()));
            } else if (cl.equals(float.class)) {
                field.set(o, Float.parseFloat(entry.getValue()));
            } else if (cl.equals(boolean.class)) {
                field.set(o, Boolean.parseBoolean(entry.getValue()));
            } else if (cl.equals(char.class)) {
                field.set(o, entry.getValue().charAt(0));
            } else if (cl.equals(String.class)) {
                field.set(o, entry.getValue());

            } else field.set(o, field.getType().cast(deSerialize(entry.getValue(), field.getType())));
        }


//        String className = pairs[0].split(":")[0];
//        Class<?> clazz = Class.forName(className);
//        for (int i = 1; i < pairs.length; i++) {
//            String[] pair = getObjectPairs(pairs[i]);
//            if (pair.length < 3)
//                break;
//            String value = pair[2];
//            String objToCast = getObjectFromString(value).toString();
//             else {
//                Class<?> cl = field.getType();
//
//                else {
//                    field.set(o, field.getType().cast(deSerialize(objToCast, SomeObject.class)));
//                }
//            }
//            if (pairs[i].contains("{"))
//                break;
//        }


//        try (FileInputStream fileInputStream = new FileInputStream(file);
//             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
//
//            object = objectInputStream.readObject();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        return o;
    }

    private Object getObjectFromString(String value) {
        return null;
    }

    private Map<String, String> getObjectPairs(String objectAsString) {
        Map<String, String> map = new LinkedHashMap<>();
        String postfix = objectAsString.substring(1, objectAsString.lastIndexOf("}"));
        String[] splitted = postfix.split(",");
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < splitted.length; i++) {
            String var = splitted[i];
            if (var.contains("{")) {
                String next;
                stringBuilder.append(var);
                stringBuilder.append(",");
                while (!(next = splitted[i + 1]).contains("}")) {
                    stringBuilder.append(next);
                    stringBuilder.append(",");
                    i++;
                }
                stringBuilder.append(next);
                i++;
                var = stringBuilder.toString();
            }
            map.put(var.substring(0, var.indexOf(":")), var.substring(var.indexOf(":") + 1));
        }
        return map;

    }

}
