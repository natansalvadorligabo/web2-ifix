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
import util.PasswordEncoder;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    public RegisterServlet() { super(); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/register.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = new User();
        user.setEmail(email);
        user.setPassword(PasswordEncoder.encode(password));

        RequestDispatcher dispatcher = null;

        UserDao userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());

        if(userDao.save(user)) {
            req.setAttribute("registrationStatus", "success");
            dispatcher = req.getRequestDispatcher("/pages/login.jsp");
        } else {
            req.setAttribute("registrationStatus", "fail");
            dispatcher = req.getRequestDispatcher("/pages/register.jsp");
        }

        dispatcher.forward(req, resp);
    }
}
