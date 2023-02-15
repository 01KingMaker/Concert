package servlet;

import event.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;


import javax.servlet.RequestDispatcher;
import java.util.Vector;
import java.io.PrintWriter;
import java.io.IOException;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class servletEvent extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            Event e = new Event();
            Object[] o = e.findAll(null);
            request.setAttribute("events", o);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("Accueil.jsp?page=Choix.jsp");
            requestDispatcher.forward(request,response);
        }
        catch(Exception e){
            out.println(e);
            e.printStackTrace();
        }
    }
}
