/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplejdbc.*;

/**
 *
 * @author aseck02
 */
@WebServlet(name = "ClientSate", urlPatterns = {"/ClientSate"})
public class ClientSate extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Création du DAO avec source de données
        response.setContentType("text/html;charset=UTF-8");

        //Création du DAO avec source de données
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ClientState</title>");
            out.println("<style> table, th, td {border: 1px solid black;border-collapse: collapse</style>");
            out.println("</head>");
            out.println("<body>");
            try {
                String State = request.getParameter("StateCode");
                if (State == null) {
                    throw new Exception("La paramètre StateCode n'a pas été transmis");

                }
                DAO dao = new DAO(DataSourceFactory.getDataSource());
                List<CustomerEntity> customers = dao.customersInState(State);
                if (customers.isEmpty() ) {
                    throw new Exception("Etat inconnu");
                }
                out.println(" <table style=\"width:30%\" border=2> ");
                out.println("<tr>");
                out.println("<th>ID</th>");
                out.println("<th>Name</th>");
                out.println("<th>Address</th>");
                out.println("</tr>");
                
                for (CustomerEntity c : customers) {
                    int id = c.getCustomerId();
                    String name = c.getName();
                    String address = c.getAddressLine1();
                    out.println("<tr>");
                    out.println("<td>" + id + "</td>");
                    out.println("<td>" + name + "</td>");
                    out.println("<td>" + address + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            } catch (Exception e) {
                out.printf("Erreur : %s", e.getMessage());
            }
            out.println("</body>");
             out.printf("<hr> <a href='%s'>Retour au menu</a>", request.getContextPath());


            out.println("</html>");
        } catch (Exception ex) {
            Logger.getLogger("ShowClient").log(Level.SEVERE, "Erreur de traitement", ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
