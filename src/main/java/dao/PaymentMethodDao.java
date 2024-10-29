package dao;

import model.Address;
import model.PaymentMethod;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentMethodDao {
    private final DataSource dataSource;

    public PaymentMethodDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Boolean save(PaymentMethod paymentMethod) {
        Optional<PaymentMethod> optional = getPaymentMethodById(paymentMethod.getId());
        if(optional.isPresent()) {
            return false;
        }
        String sql = "insert into payment_methods (name) values (?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, paymentMethod.getName());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    paymentMethod.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred during insert operation", e);
        }
        return true;
    }

    public Optional<PaymentMethod> getPaymentMethodById(Long id) {
        String sql = "select * from payment_methods where id = ?";
        Optional<PaymentMethod> optional = Optional.empty();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PaymentMethod paymentMethod = new PaymentMethod();
                    paymentMethod.setId(rs.getLong("id"));
                    paymentMethod.setName(rs.getString("name"));
                    optional = Optional.of(paymentMethod);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return optional;
    }

    public List<PaymentMethod> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        String sql = "select * from payment_methods";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PaymentMethod paymentMethod = new PaymentMethod();
                paymentMethod.setId(rs.getLong("id"));
                paymentMethod.setName(rs.getString("name"));

                paymentMethods.add(paymentMethod);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }

        return paymentMethods;
    }
}
