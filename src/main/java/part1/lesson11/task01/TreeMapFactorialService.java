package part1.lesson11.task01;

import lombok.ToString;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

@ToString
public class TreeMapFactorialService implements FactorialService {
    private Map<Integer, BigInteger> mapResult;

    public TreeMapFactorialService() {
        this.mapResult = new TreeMap<>();
    }

    @Override
    public Map<Integer, BigInteger> findFactorials(Integer[] numbers) {
        Arrays.stream(numbers).forEach(num -> mapResult.put(num, null));

        AtomicInteger lastCountedFor = new AtomicInteger(0);
        mapResult.entrySet().stream().forEach(entry -> {
            mapResult.put(entry.getKey(), getFactorial(entry.getKey(), lastCountedFor.get()));
            lastCountedFor.getAndSet(entry.getKey() > 1 ? entry.getKey() : 0);
        });

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
