package com.ravi.concurrency.basics;

public class CalculatorImplExtends extends Thread {

    private final int val;

    private CalculatorImplExtends(int count) {
        this.val = count;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + val + " * " + i + "=" + val * i);
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            CalculatorImplExtends calculator = new CalculatorImplExtends(i);
            Thread thread = new Thread(calculator);
            thread.start();
        }
    }
}
