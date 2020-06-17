/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee4216.lab2.bestseller;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author yatha
 */
public class scrap {
    
    public static void storeBookInfo(Document page) throws IOException, SQLException
    {
         
        Connection con = null;
        //Statement st = null;
        String url = "jdbc:derby://localhost:1527/Amazon";
        String user = "test";
        String password = "test";
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(true); 
       // st = con.createStatement();

       Elements parentInfo = page.select("span.a-list-item");
         for (Element parent: parentInfo){
             
            
             String s = "span.zg-badge-text";
             PreparedStatement stmt=con.prepareStatement("INSERT INTO BOOK (LINK_OF_THE_IMAGE,RANKING,TITLE,AUTHOR,PRICE,TIMESTAMP) VALUES (?,?,?,?,?,?)");  

stmt.setString(1,parent.getElementsByTag("img").attr("abs:src"));  
stmt.setString(2,parent.select("span.zg-badge-text").text()); 
stmt.setString(3,parent.select("div.p13n-sc-truncate").text()); 
stmt.setString(4,parent.select("a+div.a-size-small").text()); 
stmt.setString(5,parent.select("span.p13n-sc-price").text()); 
stmt.setTime(6,java.sql.Time.valueOf(LocalTime.now())); 

         
      // String values = "'"+parent.getElementsByTag("img").attr("abs:src")+"','"+parent.select("span.zg-badge-text").text()+"','"+parent.select("div.p13n-sc-truncate").text().replace("'", "")+"','"+parent.select("a+div.a-size-small").text()+"','"+parent.select("span.p13n-sc-price").text()+"',"+java.sql.Time.valueOf(LocalTime.now());
        //   System.out.println(values);
          // st.executeUpdate("INSERT INTO BOOK (LINK_OF_THE_IMAGE,RANKING,TITLE,AUTHOR,PRICE,TIMESTAMP) VALUES ("+values+")");
           stmt.executeUpdate();
           
                   }
         //st.close();
        con.close();
        
    }
    
    public static void main(String args[]) throws IOException, SQLException{
        String url="https://www.amazon.com/gp/bestsellers/books/";
        Document page;
        page = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36").get();
        //System.out.println(page);
        final File f;
        f = new File("d:\\scrap.html");
        FileUtils.writeStringToFile(f, page.outerHtml(), "UTF-8");//CHECKPOINT 3

        storeBookInfo(page);
       
        Element next = page.selectFirst("li.a-last > a");
        while(next != null){
        
       

        
        String s = next.attr("abs:href");
        page = Jsoup.connect(s).get();
        storeBookInfo(page);
        next = page.selectFirst("li.a-last > a");
       
        
        
        
        
   
}
    
}
}
