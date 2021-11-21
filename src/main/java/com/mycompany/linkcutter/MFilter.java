/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.linkcutter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Main-PC
 */
public class MFilter implements Filter {
    private FilterConfig filterConfig = null;

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        String czyBlokowac = filterConfig.getInitParameter("login");
        if (czyBlokowac.equals("admin")) {
            out.print("Strona jest w trakcie budowy");
        } else {
            chain.doFilter(request, response);
        }
    }
}
