package part1.lesson06.task01.service;

import lombok.ToString;

import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

@ToString
public class OneThreadFactorialService implements FactorialService {
    private Map<Integer, BigInteger> mapResult;

    public OneThreadFactorialService() {
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
