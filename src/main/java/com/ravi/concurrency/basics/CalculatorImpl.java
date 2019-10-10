package com.ravi.concurrency.basics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CalculatorImpl implements Runnable {

    private final int val;

    private CalculatorImpl(int count) {
        this.val = count;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + val + " * " + i + "=" + val * i);
        }
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        Thread.State[] ts = new Thread.State[10];
        for (int i = 1; i <= 10; i++) {
            CalculatorImpl calculator = new CalculatorImpl(i);
            Thread thread = new Thread(calculator);
            thread.setName("Thread:"+i);
            threads[i-1] = thread;
            if (i % 2 == 0) {
                thread.setPriority(Thread.MAX_PRIORITY);
            } else {
                thread.setPriority(Thread.MIN_PRIORITY);
            }
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter("./output.txt"))) {
            for (int i = 1; i <= 10; i++) {
                Thread thread = threads[i - 1];
                ts[i-1] = thread.getState();
                pw.append(String.format("Main : Id %d - %s\n", thread.getId(), thread.getName()));
                pw.append(String.format("Main : Priority: %d\n", thread.getPriority()));
                pw.append(String.format("Main : Old State: %s\n", ts[i-1]));
                pw.append(String.format("Main : New State: %s\n", thread.getState()));
                pw.append(String.format("Main : ************************************\n"));
            }
            for (int i = 1; i <= 10; i++) {
                threads[i-1].start();
            }
            boolean finished = false;
            while (!finished) {
                for (int i = 1; i <= 10; i++) {
                    Thread thread = threads[i-1];
                    if (thread.getState() != ts[i-1]) {
                        pw.append(String.format("Main : Id %d - %s\n", thread.getId(), thread.getName()));
                        pw.append(String.format("Main : Priority: %d\n", thread.getPriority()));
                        pw.append(String.format("Main : Old State: %s\n", ts[i-1]));
                        pw.append(String.format("Main : New State: %s\n", thread.getState()));
                        pw.append(String.format("Main : ************************************\n"));
                        ts[i-1] = thread.getState();
                    }
                }
                finished = true;
                for (int i=1;i<=10;i++){
                    finished = finished && (threads[i-1].getState() == Thread.State.TERMINATED);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
