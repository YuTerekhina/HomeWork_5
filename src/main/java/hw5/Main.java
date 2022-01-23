package hw5;

import static java.lang.System.arraycopy;

public class Main {
    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {
        firstArrays();
        secondArrays();
    }

    public static void firstArrays() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время работы одного потока: " + (System.currentTimeMillis() - startTime) + " мс.");
    }

    public static void secondArrays() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();
        final float firstHalf[] = new float[HALF];
        float secondHalf[] = new float[HALF];
        final Thread thread1 = new Thread(() -> {
            arraycopy(arr, 0, firstHalf, 0, HALF);
            for (int i = 0; i < firstHalf.length; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        final Thread thread2 = new Thread(() -> {
            arraycopy(arr, 0, HALF, 0, HALF);
            for (int i = 0; i < secondHalf.length; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        thread1.start();
        thread2.start();

            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        arraycopy(firstHalf, 0, arr, 0, HALF);
        arraycopy(secondHalf, 0, arr, HALF, HALF);

        System.out.println("Время работы двух потоков: " + (System.currentTimeMillis() - startTime) + " мс.");
    }
}