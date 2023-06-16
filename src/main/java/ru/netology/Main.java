package ru.netology;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    public static AtomicInteger countThree = new AtomicInteger(0);
    public static AtomicInteger countFour = new AtomicInteger(0);
    public static AtomicInteger countFive = new AtomicInteger(0);

    public static void addCounter(int count) {
        switch (count) {
            case (3) -> countThree.getAndIncrement();
            case (4) -> countFour.getAndIncrement();
            case (5) -> countFive.getAndIncrement();
            default -> {
            }
        }
    }

    public static boolean palindrom(String word) {
        for (int i = 0; i < word.length() / 2; i++) {
            if ((word.charAt(i)) != (word.charAt(word.length() - 1 - i))) {
                return false;
            }
        }
        return true;

    }

    public static boolean identicalLetters(String word) {
        for (int i = 1; i < word.length() - 1; i++) {
            if ((word.charAt(0)) != (word.charAt(i))) {
                return false;
            }
        }
        return true;

    }

    public static boolean ascendingLetters(String word) {
        for (int i = 0; i < word.length() - 1; i++) {
            if ((word.charAt(i)) > (word.charAt(i + 1))) {
                return false;
            }
        }
        return true;

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Runnable palindromlogic = () -> {
            for (String text :
                    texts) {
                if (palindrom(text)) {
                    addCounter(text.length());
                }
            }
        };


        Runnable identicalLettersLogic = () -> {
            for (String text :
                    texts) {
                if (identicalLetters(text)) {
                    addCounter(text.length());
                }
            }
        };
        Runnable ascendingLetterslogic = () -> {
            for (String text :
                    texts) {
                if (ascendingLetters(text)) {
                    addCounter(text.length());
                }
            }
        };

        Thread thread1 = new Thread(palindromlogic);
        threads.add(thread1);
        thread1.start();
        Thread thread2 = new Thread(identicalLettersLogic);
        threads.add(thread2);
        thread2.start();
        Thread thread3 = new Thread(ascendingLetterslogic);
        threads.add(thread3);
        thread3.start();


        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("Красивых слов с длиной 3: " + countThree.get());
        System.out.println("Красивых слов с длиной 4: " + countFour.get());
        System.out.println("Красивых слов с длиной 5: " + countFive.get());
    }
}
