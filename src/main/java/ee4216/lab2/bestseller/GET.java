/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee4216.lab2.bestseller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author yatha
 */
public class GET extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html");
            Connection con = null;
            //Statement st = null;
            String url = "jdbc:derby://localhost:1527/Amazon";
            String user = "test";
            String password = "test";
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(true);
            PreparedStatement ps=con.prepareStatement("select * from book");
            ResultSet  rs=ps.executeQuery();
            PrintWriter out=response.getWriter();
            out.println("<html><body><table><tr><td>Image</td><td>Ranking</td><td>Title</td><td>Author</td><td>Price</td></tr>");
            while(rs.next())
            {
                out.println("<tr><td><img src="+rs.getString(1)+"></td><td>"+rs.getString(2)+"</tr><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td></tr>");
                
            }
            out.println("</table></body></html>");
            
        } catch (SQLException ex) {
            Logger.getLogger(GET.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
