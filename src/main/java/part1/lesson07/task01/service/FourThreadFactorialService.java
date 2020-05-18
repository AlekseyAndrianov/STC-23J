package part1.lesson07.task01.service;

import lombok.ToString;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@ToString
public class FourThreadFactorialService implements FactorialService {
    private Map<Integer, BigInteger> mapResult;

    public FourThreadFactorialService() {
        this.mapResult = new TreeMap<>();
    }

    @Override
    public Map<Integer, BigInteger> findFactorials(Integer[] numbers) {
        for (Integer num : numbers) {
            mapResult.put(num, null);
        }

        Integer lastCountedFor = 0;
        for (Map.Entry<Integer, BigInteger> num : mapResult.entrySet()) {
            mapResult.put(num.getKey(), getFactorial(num.getKey(), lastCountedFor));
            lastCountedFor = num.getKey() > 1 ? num.getKey() : 0;
        }
        return mapResult;
    }

    private BigInteger getFactorial(Integer number, Integer lastCountedFor) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        if (number <= 1)
            return BigInteger.valueOf(1);


        int firstValue = lastCountedFor == 0 ? 1 : lastCountedFor;

        int[] firstIndex = new int[]{
                firstValue,
                (int) (firstValue + (number - firstValue) / 4.0),
                (int) (firstValue + (number - firstValue) / 2.0),
                (int) (firstValue + (number - firstValue) / 4.0 * 3)};

        int[] lastIndex = new int[]{
                (int) (firstValue + (number - firstValue) / 4.0),
                (int) (firstValue + (number - firstValue) / 2.0),
                (int) (firstValue + (number - firstValue) / 4.0 * 3),
                number};
        List<Future<BigInteger>> futures = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            Future<BigInteger> future = executorService.submit(() -> {

                BigInteger threadResult = BigInteger.valueOf(1);
                for (int ind = firstIndex[finalI]; ind < lastIndex[finalI]; ind++) {
                    threadResult = threadResult.multiply(BigInteger.valueOf(ind));
                }
                return threadResult;
            });
            futures.add(future);
        }
        List<BigInteger> futureResults = new ArrayList<>();
        BigInteger result = BigInteger.valueOf(1);

        for (int i = 0; i < 4; i++) {
            try {
                futureResults.add(futures.get(i).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 4; i++) {
            result = result.multiply(futureResults.get(i));
        }
        executorService.shutdown();
        if (lastCountedFor != 0)
            return result.multiply(mapResult.get(lastCountedFor));
        return result;
    }

}
