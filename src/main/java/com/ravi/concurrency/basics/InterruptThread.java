package com.ravi.concurrency.basics;

public class InterruptThread extends Thread {

    @Override
    public void run() {
        long num = 1L;
        while (true) {
            if (isPrime(num)) {
                System.out.println("Prime:" + num);
            }
//            if (interrupted()) {Updates the state
            if(isInterrupted()){//Doesnt update the state
                System.out.println("Interrupted");
                return;
            }
            num++;
        }

    }

    private boolean isPrime(long num) {
        if(num <= 2){
            return true;
        }
        for (int i = 2; i <= (num / 2); i++) {
            if ((num % i) == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new InterruptThread();
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }
}
