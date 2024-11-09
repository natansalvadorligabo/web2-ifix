package servlets;

import dao.UserDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import util.DataSourceSearcher;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public LoginServlet() { super(); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        String url;

        UserDao userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());

        Optional<User> optional = userDao.getUserByEmailAndPassword(email, password);
        if(optional.isPresent()) {
            User user = optional.get();
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(60 * 30); // 30 minutes
            session.setAttribute("user", user);
            url = "/home";
        }else {
            req.setAttribute("result", "loginError");
            url = "/pages/login.jsp";
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher(url);
        dispatcher.forward(req, resp);
    }
}
