package part1.lesson11.task01;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Задание:
 * Перевести одну из предыдущих работ на использование стримов и лямбда-выражений там,
 * где это уместно (возможно, жертвуя производительностью)
 * */
public class Main {

    public static void main(String[] args) {


        List<PojoObjectForStream> list = PojoGenerator.generateObjects(100);
        list.stream()
                .filter(pojo -> pojo.getName().length() > 2)
                .filter(pojo -> pojo.getSize() > 50)
                .limit(20L)
                .distinct()
                .map(pojo -> {
                    Map<String, Integer> map = new HashMap<>();
                    map.put(pojo.getName(), pojo.getSize());
                    return map.entrySet().iterator().next();
                })
                .forEach((entry) -> System.out.println(String.format("key: '%s'; value: '%d'", entry.getKey(), entry.getValue())));

        try {
            String result1 = FactorialServiceExecutor
                    .getAverageExecutionTime(10, TreeMapFactorialService.class);
            System.out.println(result1);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
