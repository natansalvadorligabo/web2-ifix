package servlets;

import dao.AddressDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

import dao.CustomerDao;
import jakarta.servlet.http.HttpSession;
import model.Address;
import model.Customer;
import util.DataSourceSearcher;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	@Serial
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/customer.jsp");
		dispatcher.forward(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");
		String cpf = req.getParameter("cpf");

		String street = req.getParameter("street");
		String number = req.getParameter("number");
		String complement = req.getParameter("complement");
		String neighborhood = req.getParameter("neighborhood");
		String cep = req.getParameter("cep");
		String city = req.getParameter("city");
		String state = req.getParameter("state");

		Address address = new Address();
		address.setStreet(street);
		address.setNumber(number);
		address.setComplement(complement);
		address.setNeighborhood(neighborhood);
		address.setCep(cep);
		address.setCity(city);
		address.setState(state);

		Customer customer = new Customer();
		customer.setAddress(address);
		customer.setName(name);
		customer.setEmail(email);
		customer.setPhone(phone);
		customer.setCpf(cpf);
		customer.setActive(true);

		RequestDispatcher dispatcher = null;

		CustomerDao customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());
		AddressDao addressDao = new AddressDao(DataSourceSearcher.getInstance().getDataSource());
		Long addressId = addressDao.save(address);

		if (addressId > 0) {
			customer.getAddress().setId(addressId);
			if(customerDao.save(customer)) {
				HttpSession session = req.getSession();
				session.setAttribute("customer", customer);
				req.setAttribute("registrationStatus", "success");
				dispatcher = req.getRequestDispatcher("home");
			} else {
				req.setAttribute("registrationStatus", "fail");
				dispatcher = req.getRequestDispatcher("/pages/customer.jsp");
			}
		} else {
			req.setAttribute("registrationStatus", "fail");
			dispatcher = req.getRequestDispatcher("/pages/customer.jsp");
		}

		dispatcher.forward(req, res);
	}

}
