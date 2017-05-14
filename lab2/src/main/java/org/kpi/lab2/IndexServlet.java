package org.kpi.lab2;

import org.kpi.lab2.dao.UserDao;
import org.kpi.lab2.dao.UserDaoUtils;
import org.kpi.lab2.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "IndexServlet", urlPatterns = {""}, loadOnStartup = 1)
public class IndexServlet extends HttpServlet {
    UserDao dao = new UserDao();

    {
        UserDaoUtils.populateDao(dao);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
        request.setAttribute("users", dao.getAll());
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("user_name");
        dao.add(new User(name));
        request.setAttribute("users", dao.getAll());
        request.getRequestDispatcher("index.jsp").forward(request, response);
        System.out.println(request.getMethod());
    }
}
