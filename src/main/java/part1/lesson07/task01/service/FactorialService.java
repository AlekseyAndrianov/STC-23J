package part1.lesson07.task01.service;

import java.math.BigInteger;
import java.util.Map;

public interface FactorialService {
    Map<Integer, BigInteger> findFactorials(Integer[] numbers);
}
