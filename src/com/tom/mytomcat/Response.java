package com.tom.mytomcat;

import java.io.PrintWriter;

public class Response {
    private PrintWriter printWriter;

    public Response(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public void write(String msg){
        printWriter.write(msg);
        printWriter.flush();
    }
}
