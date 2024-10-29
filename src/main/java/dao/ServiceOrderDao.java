package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import model.Customer;
import model.PaymentMethod;
import model.ServiceOrder;
import model.Status;
import util.DataSourceSearcher;

public class ServiceOrderDao {
    private DataSource dataSource;

    public ServiceOrderDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Boolean save(ServiceOrder serviceOrder) {
        Optional<ServiceOrder> optional = getServiceOrderById(serviceOrder.getId());
        if(optional.isPresent()) {
            return false;
        }
        String sql = "insert into service_orders (description, emission_date, finished_date, value, observation, customer_id, payment_method_id, status) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, serviceOrder.getDescription());
            ps.setDate(2, java.sql.Date.valueOf(serviceOrder.getEmissionDate()));
            ps.setDate(3, java.sql.Date.valueOf(serviceOrder.getFinishedDate()));
            ps.setDouble(4, serviceOrder.getValue());
            ps.setString(5, serviceOrder.getObservation());
            ps.setLong(6, serviceOrder.getCustomer().getId());
            ps.setLong(7, serviceOrder.getPaymentMethod().getId());
            ps.setString(8, serviceOrder.getStatus().toString());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }

        return true;
    }

    public Optional<ServiceOrder> getServiceOrderById(Long id) {
        String sql = "select * from service_orders where service_order_id = ?";
        Optional<ServiceOrder> optional = Optional.empty();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ServiceOrder serviceOrder = new ServiceOrder();
                    serviceOrder.setId(rs.getLong("service_order_id"));
                    serviceOrder.setDescription(rs.getString("description"));
                    serviceOrder.setEmissionDate(rs.getDate("emission_date").toLocalDate());
                    serviceOrder.setFinishedDate(rs.getDate("finished_date").toLocalDate());
                    serviceOrder.setValue(rs.getDouble("value"));
                    serviceOrder.setObservation(rs.getString("observation"));

                    serviceOrder.setCustomer(new Customer());
                    serviceOrder.getCustomer().setId(rs.getLong("customer_id"));

                    serviceOrder.setPaymentMethod(new PaymentMethod());
                    serviceOrder.getPaymentMethod().setId(rs.getLong("payment_method_id"));

                    serviceOrder.setStatus(Status.valueOf(rs.getString("status")));

                    optional = Optional.of(serviceOrder);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }

        return optional;
    }

    public List<ServiceOrder> getAllServiceOrders() {
        List<ServiceOrder> serviceOrders = new ArrayList<>();
        String sql = "select * from service_orders";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ServiceOrder serviceOrder = new ServiceOrder();
                serviceOrder.setId(rs.getLong("service_order_id"));
                serviceOrder.setDescription(rs.getString("description"));
                serviceOrder.setEmissionDate(rs.getDate("emission_date").toLocalDate());
                serviceOrder.setFinishedDate(rs.getDate("finished_date").toLocalDate());
                serviceOrder.setValue(rs.getDouble("value"));
                serviceOrder.setObservation(rs.getString("observation"));

                serviceOrder.setCustomer(new Customer());
                serviceOrder.getCustomer().setId(rs.getLong("customer_id"));

                CustomerDao customerDao = new CustomerDao(DataSourceSearcher.getInstance().getDataSource());
                Optional<Customer> customer = customerDao.getCustomerById(serviceOrder.getCustomer().getId());
                serviceOrder.getCustomer().setName(customer.get().getName());

                serviceOrder.setPaymentMethod(new PaymentMethod());
                serviceOrder.getPaymentMethod().setId(rs.getLong("payment_method_id"));

                PaymentMethodDao paymentMethodDao = new PaymentMethodDao(DataSourceSearcher.getInstance().getDataSource());
                Optional<PaymentMethod> paymentMethod = paymentMethodDao.getPaymentMethodById(serviceOrder.getPaymentMethod().getId());
                serviceOrder.getPaymentMethod().setName(paymentMethod.get().getName());

                serviceOrder.setStatus(Status.valueOf(rs.getString("status")));
                serviceOrders.add(serviceOrder);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }

        return serviceOrders;
    }
}
