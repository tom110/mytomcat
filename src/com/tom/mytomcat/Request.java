package com.tom.mytomcat;

import com.tom.servlet.HttpServlet;

import java.util.Map;

public class Request {
    private String path;
    private String parameter;
    private String method;
    private Map<String,String> attribute;


    public HttpServlet initServlet(){
        return ServletContainer.getHttpServlet(path);
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParameter() {
        return parameter;
    }

    public String getParameter(String name){
        return attribute.get(name);
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getAttribute() {
        return attribute;
    }

    public void setAttribute(Map<String, String> attribute) {
        this.attribute = attribute;
    }
}
