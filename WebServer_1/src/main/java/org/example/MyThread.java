package org.example;

import java.io.IOException;
import java.util.Queue;

public class MyThread extends Thread {
    private final int id;

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    private Processor processor;

    public MyThread(int id, Processor processor) {
        this.id = id;
        this.processor = processor;
    }

    @Override
    public void run() {
        System.out.println("Thread No " + id);

        try {
            processor.process();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
