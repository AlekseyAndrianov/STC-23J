package part1.lesson07.task01.service;

import java.math.BigInteger;
import java.util.*;

public class HashMapFactorialService implements FactorialService {
    private Map<Integer, BigInteger> mapResult;

    public HashMapFactorialService() {
        this.mapResult = new HashMap<>();
    }

    @Override
    public Map<Integer, BigInteger> findFactorials(Integer[] numbers) {
        for (Integer num : numbers) {
            mapResult.put(num, null);
        }

        List<Integer> ints = Arrays.asList(numbers);
        Collections.sort(ints, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        Integer lastCountedFor = 0;
        for (Integer num : ints) {
            mapResult.put(num, getFactorial(num, lastCountedFor));
            lastCountedFor = num > 1 ? num : 0;
        }
        return mapResult;
    }

    private BigInteger getFactorial(Integer number, Integer lastCountedFor) {
        if (number <= 1)
            return BigInteger.valueOf(1);

        BigInteger result = BigInteger.valueOf(1);

        for (int i = lastCountedFor == 0 ? 1 : lastCountedFor; i <= number; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        if (lastCountedFor != 0)
            return result.multiply(mapResult.get(lastCountedFor));
        return result;
    }
}
