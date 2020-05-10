package part1.lesson02.task02;

public class NumberService {

    /**
     * Output all numbers meets the requirements
     *
     * @param N
     */
    public void outputNumbers(int N) {
        for (int i = 0; i < N; i++) {
            int k = getRandomNumber();
            if (k < 0)
                throw new IllegalStateException("Problem with sqrt function for k = " + k);
            double q = Math.sqrt(k);
            if ((int) Math.pow((int) q, 2) == k)
                System.out.println(k);

        }
    }

    /**
     * Function create random numbers from -Integer.MIN_VALUE to Integer.MAX_Value
     *
     * @return
     */
    public Integer getRandomNumber() {
        int positiveAspect = Math.random() > 0.5 ? 1 : -1;
        return (int) (Math.random() * Integer.MAX_VALUE) * positiveAspect;
    }

}
