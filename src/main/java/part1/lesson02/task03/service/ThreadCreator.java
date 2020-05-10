package part1.lesson02.task03.service;

import part1.lesson02.task03.Person;

public class ThreadCreator extends Thread {
        int firstIndex;
        int lastIndex;
        Integer lettersLength;
        Person[] people;

        public ThreadCreator(int firstIndex, int lastIndex, int lettersLength, Person[] people) {
            this.firstIndex = firstIndex;
            this.lastIndex = lastIndex;
            this.lettersLength = lettersLength;
            this.people = people;
        }

        public void run() {
            for (int i = firstIndex; i < lastIndex; i++) {
                Person person = Person
                        .builder()
                        .age((int) (Math.random() * 100))
                        .name(getCharSequence(15, lettersLength))
                        .sex(Math.random() < 0.5 ? Person.Sex.MAN : Person.Sex.WOMAN).build();
                people[i] = person;
            }
        }

    private String getCharSequence(int nameLength, Integer lettersLength) {
        StringBuilder name = new StringBuilder();

        name.append(getCharFromLetters(true, lettersLength));
        for (int i = 0; i < nameLength; i++) {
            name.append(getCharFromLetters(false, lettersLength));
        }
        return name.toString();
    }

    private char getCharFromLetters(boolean isUpperCase, Integer lettersLength) {
        char simbol = PersonService.letters.charAt((int) (Math.random() * lettersLength));
        return isUpperCase ? simbol : Character.toLowerCase(simbol);
    }
}
