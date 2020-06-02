package part1.lesson11.task01;

import java.math.BigInteger;
import java.util.Map;

public interface FactorialService {
    Map<Integer, BigInteger> findFactorials(Integer[] numbers);
}
