package servlets;

import dao.AddressDao;
import dao.CustomerDao;
import dao.PaymentMethodDao;
import dao.ServiceOrderDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import util.DataSourceSearcher;

import java.io.IOException;
import java.io.Serial;
import java.text.NumberFormat;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        HttpSession session = req.getSession(false);

        CustomerDao customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());
        ServiceOrderDao serviceOrderDao = new ServiceOrderDao(DataSourceSearcher.getInstance().getDataSource());
        AddressDao addressDao = new AddressDao(DataSourceSearcher.getInstance().getDataSource());
        Statistics statistics = new Statistics();

        List<Customer> customers = customerDao.getAllCustomers();
        List<ServiceOrder> serviceOrders = serviceOrderDao.getAllServiceOrders();
        List<Address> addresses = addressDao.getAllAddresses();

        double totalRevenue = statistics.calculateTotalRevenue(serviceOrders);
        double averageOrderValue = statistics.calculateAverageOrderValue(serviceOrders);
        double completedPercentage = statistics.calculateCompletedServicePercentage(serviceOrders);
        long ordersRemaining = statistics.calculateOrdersRemaining(serviceOrders);

        req.setAttribute("customers", customers);
        req.setAttribute("serviceOrders", serviceOrders);
        req.setAttribute("addresses", addresses);
        req.setAttribute("totalRevenue", NumberFormat.getCurrencyInstance().format(totalRevenue));
        req.setAttribute("averageOrderValue", NumberFormat.getCurrencyInstance().format(averageOrderValue));
        req.setAttribute("approvedPercentage", completedPercentage);
        req.setAttribute("ordersRemaining", ordersRemaining);

//        if (session != null) {
//            Employee employee = (Employee) session.getAttribute("employee");
//            if (customer != null) {
//                session.setAttribute("employee", employee);
//            } else {
//                req.setAttribute("errorMessage", "no employee found in session");
//            }
//        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/home.jsp");
        dispatcher.forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
