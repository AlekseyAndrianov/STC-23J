package part1.lesson08.task01.service;

import part1.lesson08.task01.entities.SomeObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectDeSerializer {


    public Object executeDeSerializing(String file) throws NoSuchFieldException, InstantiationException, IllegalAccessException {

        String objectAsString = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            objectAsString = reader.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object o = deSerialize(objectAsString, SomeObject.class);
        SomeObject someObject = (SomeObject) o;
        System.out.println("DeSerialized: " + someObject);
        return someObject;
    }

    // При вызове нужно передать строку соответствующую объекту
    public Object deSerialize(String objectAsString, Class<?> someObjectClass) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        Map<String, String> objectFields = getObjectPairs(objectAsString);

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
        return o;
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
