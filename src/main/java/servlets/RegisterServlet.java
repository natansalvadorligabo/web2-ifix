package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import dao.CustomerDao;
import model.Customer;
import util.DataSourceSearcher;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.getWriter().append("Served at: ").append(req.getContextPath());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");
		String cpf = req.getParameter("cpf");
		
		Customer customer = new Customer();
		customer.setName(name);
		customer.setEmail(email);
		customer.setPhone(phone);
		customer.setCpf(cpf);
		customer.setActive(true);
		
		RequestDispatcher dispatcher = null;

		CustomerDao customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());

		if(customerDao.save(customer)) {
			req.setAttribute("registrationStatus", "success");
			dispatcher = req.getRequestDispatcher("/pages/register.jsp");
		} else {
			req.setAttribute("registrationStatus", "fail");
			dispatcher = req.getRequestDispatcher("/pages/register.jsp");
		}

		dispatcher.forward(req, res);

	}

}
