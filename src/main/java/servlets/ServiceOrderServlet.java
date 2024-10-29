package servlets;

import dao.CustomerDao;
import dao.PaymentMethodDao;
import dao.ServiceOrderDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import model.PaymentMethod;
import model.ServiceOrder;
import model.Status;
import util.DataSourceSearcher;

import java.io.IOException;
import java.io.Serial;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@WebServlet("/serviceOrder")
public class ServiceOrderServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    public ServiceOrderServlet() { super(); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        CustomerDao customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());
        PaymentMethodDao paymentMethodDao = new PaymentMethodDao(DataSourceSearcher.getInstance().getDataSource());

        List<Customer> customers = customerDao.getAllCustomers();
        List<PaymentMethod> paymentMethods = paymentMethodDao.getAllPaymentMethods();

        req.setAttribute("customers", customers);
        req.setAttribute("paymentMethods", paymentMethods);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/serviceOrder.jsp");
        dispatcher.forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = request.getParameter("description");
        String emissionDate = request.getParameter("emissionDate");
        String finishedDate = request.getParameter("finishedDate");
        Double value = Double.parseDouble(request.getParameter("value"));
        String observation = request.getParameter("observation");
        Long customerId = Long.parseLong(request.getParameter("customerId"));
        Long paymentMethodId = Long.parseLong(request.getParameter("paymentMethod"));
        String status = request.getParameter("status");

        CustomerDao customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());
        PaymentMethodDao paymentMethodDao = new PaymentMethodDao(DataSourceSearcher.getInstance().getDataSource());

        Optional<Customer> customer = customerDao.getCustomerById(customerId);
        Optional<PaymentMethod> paymentMethod = paymentMethodDao.getPaymentMethodById(paymentMethodId);

        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setDescription(description);
        serviceOrder.setEmissionDate(LocalDate.parse(emissionDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        serviceOrder.setFinishedDate(LocalDate.parse(finishedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        serviceOrder.setValue(value);
        serviceOrder.setObservation(observation);
        serviceOrder.setCustomer(customer.orElse(null));
        serviceOrder.setPaymentMethod(paymentMethod.orElse(null));
        serviceOrder.setStatus(Status.valueOf(status));

        ServiceOrderDao serviceOrderDao = new ServiceOrderDao(DataSourceSearcher.getInstance().getDataSource());
        serviceOrder.setId(0L);

        if (serviceOrderDao.save(serviceOrder)) {
            response.sendRedirect("/pages/home.jsp");
        } else {
            response.sendRedirect("/pages/home.jsp");
        }
    }
}
