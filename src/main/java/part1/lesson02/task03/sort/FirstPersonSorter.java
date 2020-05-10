package part1.lesson02.task03.sort;

import part1.lesson02.task03.Person;

import java.util.function.Predicate;

public class FirstPersonSorter implements PersonSorter {
    public void sort(Person[] people) {

        Predicate<Person[]> sexPredicate = per -> per[0].getSex() == Person.Sex.WOMAN && per[1].getSex() == Person.Sex.MAN;
        sortByPredicate(people, 0, people.length, sexPredicate);

        final int firstWomenIndex = findFirstWomenIndex(people);
        Predicate<Person[]> agePredicate = (p) -> p[0].getAge() < p[1].getAge();
        Thread manSortingByAge = new SortingThread(people, 0, firstWomenIndex, agePredicate);
        Thread womanSortingByAge = new SortingThread(people, firstWomenIndex, people.length, agePredicate);
        manSortingByAge.start();
        womanSortingByAge.start();
        try {
            manSortingByAge.join();
            womanSortingByAge.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Predicate<Person[]> namePredicate = pr -> pr[0].getAge() == pr[1].getAge() && pr[0].getName().compareTo(pr[1].getName()) > 0;
        Thread manSortingByName = new SortingThread(people, 0, firstWomenIndex, namePredicate);
        Thread womanSortingByName = new SortingThread(people, 0, people.length, namePredicate);
        manSortingByName.start();
        womanSortingByName.start();
        try {
            manSortingByName.join();
            womanSortingByName.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int findFirstWomenIndex(Person[] people) {
        int center = people.length / 2;
        int leftInd = 0;
        int rightInd = 0;
        for (int i = center; i >= 0; i--) {
            if (people[i].getSex() == Person.Sex.MAN) {
                leftInd = i;
                break;
            }
        }
        for (int i = center + 1; i < people.length; i++) {
            if (people[i].getSex() == Person.Sex.WOMAN) {
                rightInd = i;
                break;
            }
        }
        return leftInd == center ? rightInd : leftInd + 1;
    }

    private void sortByPredicate(Person[] people, int firstIndex, int lastIndex, Predicate<Person[]> predicate) {
        int count = 1;
        while (count > 0) {
            count = 0;
            for (int i = firstIndex; i < lastIndex - 1; i++) {
                if (predicate.test(new Person[]{people[i], people[i + 1]})) {
                    changePersonsInArray(people, i);
                    count++;
                }
            }
        }
    }

    private void changePersonsInArray(Person[] people, int i) {
        Person man = people[i + 1];
        Person woman = people[i];
        people[i + 1] = woman;
        people[i] = man;
    }

    private class SortingThread extends Thread {
        Person[] people;
        int firstIndex;
        int lastIndex;
        Predicate<Person[]> predicate;

        private SortingThread(Person[] people, int firstIndex, int lastIndex, Predicate<Person[]> predicate) {
            this.people = people;
            this.firstIndex = firstIndex;
            this.lastIndex = lastIndex;
            this.predicate = predicate;
        }

        @Override
        public void run() {
            sortByPredicate(people, firstIndex, lastIndex, predicate);
        }
    }
}
