package servlet;

import event.Event;
import event.Zone;

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
import utils.Fonction;

public class servletPayer  extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            String date = request.getParameter("date");
            date = date.trim();
            String places = request.getParameter("place");
            places = places.trim();
            String idEvent = request.getParameter("idEvent");
            idEvent = idEvent.trim();
            java.sql.Timestamp timestamp = Fonction.convertStringToTimestamp(date);
            
            out.println(places);
            out.print(idEvent);
            out.println(timestamp);

            Vector<String[]> rep = Fonction.separate(places);
            
            Fonction f = new Fonction();
            Zone z = new Zone();
            z.checkIfInZone(rep, idEvent);

            // String idEvent = request.getParameter("id");
            // Event e = new Event();
            // e.setIdEvent(idEvent);
            // Object o = e.findById(null);
            // request.setAttribute("events", o);
            // RequestDispatcher requestDispatcher = request.getRequestDispatcher("Accueil.jsp?page=Reservation.jsp");
            // requestDispatcher.forward(request,response);
        }
        catch(Exception e){
            request.setAttribute("error", e.getMessage());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("Accueil.jsp?page=erreur.jsp");
            requestDispatcher.forward(request,response);
        }
    }
}
