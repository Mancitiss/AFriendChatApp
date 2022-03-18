package com.mycompany.afriendserver;

public class test {
    private static class testclass implements Runnable{
        int time;
        public testclass(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            for(int i = 0; i < 100; i++){
                System.out.println("Thread: " + i + " " + time);
                try {
                    synchronized(this){
                        wait(time);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        // create 2 threads
        Thread t1 = new Thread(new testclass(15));
        Thread t2 = new Thread(new testclass(150));
        t1.start();
        t2.start();
        for(int i = 0; i < 100; i++){
            System.out.println("Main: " + i + " 100");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
