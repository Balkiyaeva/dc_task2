package org.example;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Processor of HTTP request.
 */
public class Processor {
    private final Socket socket;
    private final HttpRequest request;

    public Processor(Socket socket, HttpRequest request) {
        this.socket = socket;
        this.request = request;
    }

    public void process() throws IOException, InterruptedException {
        // Print request that we received.
        System.out.println("Got request:");
        System.out.println(request.toString());
        System.out.flush();

        // To send response back to the client.
        PrintWriter output = new PrintWriter(socket.getOutputStream());

        if (request.getRequestLine().contains("create")) {
            process_create(output);
        } else if (request.getRequestLine().contains("delete")) {
            process_delete(output);
        } else {
            process_params(output);
        }

        socket.close();
    }

    public void process_create(PrintWriter output) throws InterruptedException {

        Thread.sleep(15000);

        // We are returning a simple web page now.
        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: text/html; charset=utf-8");
        output.println();
        output.println("<html>");
        output.println("<head><title>Create</title></head>");
        output.println("<body><h1>Created<h1></body>");
        output.println("</html>");
        output.flush();

    }

    public void process_delete(PrintWriter output) throws IOException {
        AtomicInteger countLineWPlus = new AtomicInteger();
        Path path = Paths.get("/Users/amira/Downloads");
        Files.walk(path).forEach(path1 -> {
            File file = path1.toFile();
            if (file.isFile()) {
                Scanner sc = null;
//                System.out.println(path1);
                try {
                    sc = new Scanner(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                while (true) {
                    assert sc != null;
                    if (!sc.hasNextLine()) break;
                    if (sc.nextLine().contains("+")){
                        countLineWPlus.getAndIncrement();
                    }
                }
            }
        });

        System.out.println(countLineWPlus);

        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: text/html; charset=utf-8");
        output.println();
        output.println("<html>");
        output.println("<head><title>Delete</title></head>");
        output.println("<body><h1>There are " + countLineWPlus + " lines contain '+' in the Downloads path<h1></body>");
        output.println("</html>");
        output.flush();
    }

    public void process_params(PrintWriter output) throws IOException {
        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: text/html; charset=utf-8");
        output.println();
        output.println("<html>");
        output.println("<head><title>Params</title></head>");
        output.println("""
                <body><h1>«P.S. Пожалуста если с можите положыте на могилку цветы для Элджернона. На заднем дворе.»

                Отрывок из книги: Дэниел Киз. «Цветы для Элджернона».<h1></body>""");
        output.println("</html>");
        output.flush();
    }

}
