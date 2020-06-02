package part1.lesson11.task01;

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
}
