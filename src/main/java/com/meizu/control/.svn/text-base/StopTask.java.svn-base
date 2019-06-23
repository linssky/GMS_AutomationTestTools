package com.meizu.control;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;

public class StopTask extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {
        JOptionPane.showInputDialog("是否只跑某个手机？\n" +
                "若是则输入该手机的SN号");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            // Write some content
            out.println("<html>");
            out.println("<head>");
//            out.println("<title>test</title>");
            out.println("</head>");
            out.println("<body>");
//            out.println("<h2>Servlet tst at " + request.getContextPath() + "</h2>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
    //handle post request
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("gb2312");

        PrintWriter out = response.getWriter();
        String name = request.getParameter("USERNAME");

        out.println("Your Name : " + name);
        String[] aa = new String[0];
        JOptionPane.showInputDialog("是否只跑某个手机？\n" +
                "若是则输入该手机的SN号");
        try {
            SanityTestCase.main(aa);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //destroy
    public void destroy() {}
}