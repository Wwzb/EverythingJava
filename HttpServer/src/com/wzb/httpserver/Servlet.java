package com.wzb.httpserver;

public class Servlet {
    public void service(Request req,Response rep){
        rep.println("<html><head><title>这是一个响应</title>");
        rep.println("</head><body>");
        rep.println("欢迎：").println(req.getParameter("name")).println("回来");
        rep.println("</body></html>");
    }
}
