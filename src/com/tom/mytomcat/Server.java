package com.tom.mytomcat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static ServerSocket serverSocket;
    private static ExecutorService executorService;
    private static int POOL_SIZE=15;
    private static int port=8080;

    public void start(){
        try {
            serverSocket=new ServerSocket(port);
            Socket socket=null;
            System.out.println("Starting ProtocolHandler at "+port);
            executorService= Executors.newFixedThreadPool(POOL_SIZE);
            while(true){
                socket= serverSocket.accept();
                executorService.execute(new Handler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Server().start();
    }
}
