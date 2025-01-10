package org.arcticwolf.service;

import org.arcticwolf.model.CreatePropertiesRequest;

import static org.arcticwolf.config.ConfigProperties.config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketReciever {

    public void recieve() {
        String outputDir = config.get("output.dir");
        int port = Integer.parseInt(config.get("server.port"));

        System.out.println("Server listening on port " + port);
        System.out.println("Output dir: " + outputDir);

        ExecutorService executor = Executors.newFixedThreadPool(10);
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while(true) {
                Socket socket = serverSocket.accept();
                executor.submit(() -> {
                    try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                        CreatePropertiesRequest createPropertiesRequest = (CreatePropertiesRequest) ois.readObject();

                        System.out.println("Request came fileName : " + createPropertiesRequest.getFileName() + "Properties : " + createPropertiesRequest.getProperties());

                        Properties properties = new Properties();
                        properties.putAll(createPropertiesRequest.getProperties());
                        String fileName = createPropertiesRequest.getFileName();
                        properties.store(new FileOutputStream(Paths.get(outputDir, fileName).toFile()), null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
