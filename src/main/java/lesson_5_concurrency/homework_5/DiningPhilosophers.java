package lesson_5_concurrency.homework_5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Пять безмолвных философов сидят вокруг круглого стола, перед каждым философом стоит тарелка спагетти.
 * Вилки лежат на столе между каждой парой ближайших философов.
 * Каждый философ может либо есть, либо размышлять.
 * Философ может есть только тогда, когда держит две вилки — взятую справа и слева.
 * Философ не может есть два раза подряд, не прервавшись на размышления (можно не учитывать)
 * Описать в виде кода такую ситуацию. Каждый философ должен поесть три раза
 */
public class DiningPhilosophers {

    public static void main(String[] args) {

        Philosopher[] philosophers = new Philosopher[5];
        Fork[] forks = new Fork[5];

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Fork(i);
        }

        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % forks.length]);
            new Thread(philosophers[i]).start();
        }
    }
}

class Fork {
    private final int id;
    private final Lock lock;

    public Fork(int id) {
        this.id = id;
        this.lock = new ReentrantLock();
    }

    public int getId() {
        return id;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}

class Philosopher implements Runnable {
    private final int id;
    private final Fork leftFork;
    private final Fork rightFork;
    private int meals;

    public Philosopher(int id, Fork leftFork, Fork rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.meals = 0;
    }

    private void think() throws InterruptedException {
        System.out.println("Философ " + id + " думает.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void eat() throws InterruptedException {
        leftFork.lock();
        System.out.println("Философ " + id + " взял левую вилку " + leftFork.getId());

        rightFork.lock();
        System.out.println("Философ " + id + " взял правую вилку " + rightFork.getId());

        try {
            System.out.println("Философ " + id + " ест. Порция " + (meals + 1));
            Thread.sleep((long) (Math.random() * 1000));
            meals++;
        } finally {
            leftFork.unlock();
            System.out.println("Философ " + id + " положил левую вилку " + leftFork.getId());

            rightFork.unlock();
            System.out.println("Философ " + id + " положил правую вилку " + rightFork.getId());
        }
    }

    @Override
    public void run() {
        try {
            while (meals < 3) {
                think();
                eat();
            }
            System.out.println("Философ " + id + " наелся.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
