package org.example;

import java.io.IOException;
import java.util.Queue;

public class MyThread<T> extends Thread {
    private final int id;

    private final ThreadSafeQueue<T> queue;

    public MyThread(int id, ThreadSafeQueue<T> queue) {
        this.id = id;
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Thread No " + id);

        try {
            while (true) {
                // Wait for new element.
                T elem = queue.pop();

                // Stop consuming if null is received.
                if (elem == null) {
                    return;
                }

                // Process element.
                System.out.println(id + ": get item: " + elem);

                Processor processor = (Processor) elem;
                processor.process();
            }



        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        queue.add(null, -1);
    }
}
