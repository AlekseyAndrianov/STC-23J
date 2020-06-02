package part1.lesson11.task01;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PojoGenerator {

    public static List<PojoObjectForStream> generateObjects(int count){
        List<PojoObjectForStream> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new PojoObjectForStream(UUID.randomUUID(), getPojoName(), (int)(Math.random()*100)));
        }
        return list;
    }

    private static String getPojoName() {
        StringBuilder name = new StringBuilder();
        char[] chars = {'a','b','c','d','e','f','g','h'};
        int length = (int)(Math.random()*5) +2;
        for (int i = 0; i < length; i++) {
            name.append(chars[i]);
        }
        return name.toString();

    }
}
