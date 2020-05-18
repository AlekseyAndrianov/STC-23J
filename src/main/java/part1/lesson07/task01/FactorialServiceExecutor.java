package part1.lesson07.task01;

import part1.lesson07.task01.service.FactorialService;
import part1.lesson07.task01.service.HashMapFactorialService;

import java.math.BigInteger;
import java.util.Map;

public class FactorialServiceExecutor {

    public static String getAverageExecutionTime(int countExecution, Class<? extends FactorialService> clazz) throws InstantiationException, IllegalAccessException {
        long totalTimeExecution = 0;
        for (int i = 0; i < countExecution; i++) {
            Integer[] numbers = NumberCreator.createNumbers(100);
            totalTimeExecution += executeScenario(clazz, numbers);
        }
        return String.format("Average execution time for '%d' times for class '%s' is '%d' sec.",countExecution, clazz.getSimpleName(), totalTimeExecution/1000);

    }

    private static long executeScenario(Class<? extends FactorialService> clazz, Integer[] numbers) throws IllegalAccessException, InstantiationException {

        long start = System.currentTimeMillis();
        FactorialService factorialService = clazz.newInstance();
        factorialService.findFactorials(numbers);
        return (System.currentTimeMillis() - start);
    }


    private static void executeOnlyOneScenario(){
        Integer[] numbers = NumberCreator.createNumbers(100);

        long start = System.currentTimeMillis();
        FactorialService factorialService = new HashMapFactorialService();
        Map<Integer, BigInteger> factorials = factorialService.findFactorials(numbers);

        for(Map.Entry<Integer, BigInteger> entry : factorials.entrySet()){
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println("Time execution = " + (System.currentTimeMillis() - start));
    }
}
