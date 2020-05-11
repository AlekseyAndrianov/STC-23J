package part1.lesson06.task01;

public class NumberCreator {
    public static Integer[] createNumbers(int size) {
        Integer[] numbers = new Integer[size];
        for (int i = 0; i < size; i++) {
            double random = Math.random();
            numbers[i] = (int) (random * 100000 * (random < 0.5 ? -1 : 1));
        }
        return numbers;
    }
}
