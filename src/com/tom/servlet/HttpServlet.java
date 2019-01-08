package com.tom.servlet;

import com.tom.mytomcat.Request;
import com.tom.mytomcat.Response;

import java.io.IOException;

public abstract class HttpServlet {
    public void doGet(Request request, Response response) throws IOException {
        this.service(request,response);
    }
    public void doPost(Request request, Response response) throws IOException {
        this.service(request,response);
    }
    public void service(Request request,Response response) throws IOException{
        if("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request,response);
        }else{
            doPost(request,response);
        }
    }
}
