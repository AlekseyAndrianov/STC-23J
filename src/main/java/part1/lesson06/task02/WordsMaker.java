package part1.lesson06.task02;

public class WordsMaker {
    private static final String letters = "abcdefghijklmnopqrstuvwxyz";

    public static String[] makeWords(int count) {
        int lettersLength = letters.length();
        String[] words = new String[count];
        for (int i = 0; i < count; i++) {
            int length = (int) (Math.random() * 15) + 1;
            StringBuilder word = new StringBuilder();
            for (int j = 0; j < length; j++) {
                word.append(letters.charAt((int) (Math.random() * lettersLength)));
            }
            words[i] = word.toString();
        }
        return words;
    }
}
