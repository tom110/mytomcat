package com.tom.mytomcat;

import com.tom.servlet.HttpServlet;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
                    request.setMethod(msgs[0]);

                    String[] attributesPath=msgs[1].split("\\?");
                    request.setPath(attributesPath[0]);
                    if(attributesPath.length>1){
                        Map<String,String> attributeMap=new HashMap<>();
                        String[] params=attributesPath[1].split("&");
                        for(int i=0;i<params.length;i++){
                            attributeMap.put(params[i].split("=")[0],params[i].split("=")[1]);
                        }
                        request.setAttribute(attributeMap);
                    }
                    System.out.println(msg);
                    break;
                }
            }

            if(request.getPath().endsWith("ico")){
                return;
            }

            HttpServlet httpServlet=request.initServlet();
            
            dispatcher(httpServlet,request,response);

//            writer.println("<h1>Hello World</h1>");
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

    private void dispatcher(HttpServlet httpServlet, Request request, Response response) {
        try {
            if (httpServlet == null) {
                response.write("<H1>404</H1>");
                return;
            }

            if (request.getMethod().equalsIgnoreCase("GET")) {
                httpServlet.doGet(request, response);
            }else if(request.getMethod().equalsIgnoreCase("POST")){
                httpServlet.doPost(request,response);
            }
        }catch (Exception e){
            response.write("<H1>500</H1>"+ Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
        }
    }
}
