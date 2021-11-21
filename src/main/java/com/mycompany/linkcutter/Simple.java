/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.linkcutter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Main-PC
 */
public class Simple extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        String login = request.getParameter("login");
        String haslo= request.getParameter("haslo");
        if(login.equals("admin")&&haslo.equals("qwerty"))
        {
            chain.doFilter(request, response);
        }else
        {
            out.println("login lub haslo nieprawidlowe");
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Set<Integer> generated = new LinkedHashSet<Integer>();
        Integer nextlink;
        Random rng = new Random();
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<body style='background-color:gray;'>");
            /* TODO output your page here. You may use following sample code. */
            String url = request.getRequestURL().toString();
            String []LINK = url.split("/link=");

            try {
                File myObj = new File("C:\\Users\\Public\\Documents\\servlet\\linki.txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            try {
                BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Public\\Documents\\servlet\\linki.txt"));
                String st;
                while ((st = br.readLine()) != null){
                    String[] str = st.split(";");
                    generated.add(Integer.parseInt(str[1]));
                }
                nextlink = rng.nextInt();
                generated.add(nextlink);
                Files.write(Paths.get("C:\\Users\\Public\\Documents\\servlet\\linki.txt"), (LINK[1]+";"+nextlink+"\n").getBytes(), StandardOpenOption.APPEND);

                BufferedReader br2 = new BufferedReader(new FileReader("C:\\Users\\Public\\Documents\\servlet\\linki.txt"));
                String st2;
                out.write("<style>table,tr,td{border:1px solid black;border-collapse:collapse;}td{padding:10px;}</style><table>");
                while ((st2 = br2.readLine()) != null){
                    String[] str = st2.split(";");
                    out.write("<tr><td>"+str[0]+"</td><td><a target='_blank' href='/LinkCutter/Redirect/link="+str[1]+"'>"+str[1]+"</a></td><tr>");
                }
                out.write("</table>");
            }catch (IOException e) {
            }
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
