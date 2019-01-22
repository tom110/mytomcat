package com.tom.servlet.impl;

import com.tom.mytomcat.Request;
import com.tom.mytomcat.Response;
import com.tom.servlet.HttpServlet;

import java.io.IOException;

public class MyServlet extends HttpServlet {
    @Override
    public void doGet(Request request, Response response) throws IOException {
        response.write("<H1>MyServlet</H1>"+request.getParameter("name")+":"+request.getParameter("pwd"));
    }
}
