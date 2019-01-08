package com.tom.mytomcat;

import java.io.*;
import java.net.Socket;

public class Handler implements Runnable {

    private Socket socket;
    private PrintWriter writer;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: text/html;charset=UTF-8");
            writer.println();

            Request request=new Request();
            Response response=new Response(writer);

            InputStream inputStream=socket.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            while(true){
                String msg=bufferedReader.readLine();
                if(msg==null || "".equals(msg.trim()))
                    break;

                String[] msgs=msg.split(" ");
                if(msgs.length==3 || "HTTP/1.1".equalsIgnoreCase(msgs[2])){
                    request.setPath(msgs[1]);
                    request.setMethod(msgs[0]);
                    break;
                }

                System.out.println(msg);
            }

            if(request.getPath().endsWith("ico")){
                return;
            }


            writer.println("<h1>Hello World</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.close();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
