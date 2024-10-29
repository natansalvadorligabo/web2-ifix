package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import model.Address;
import model.Customer;

public class CustomerDao {
	private DataSource dataSource;

	public CustomerDao(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public Boolean save(Customer customer){
		Optional<Customer> optional = getCustomerByEmail(customer.getEmail());
		if(optional.isPresent()) {
			return false;
		}
        String sql = "insert into customers (name, email, phone, cpf, active, address_id) values (?, ?, ?, ?, ?, ?)";
		try(Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getCpf());
            ps.setBoolean(5, customer.getActive());
			ps.setLong(6, customer.getAddress().getId());

			ps.executeUpdate();
		}catch (SQLException e) {
			throw new RuntimeException("Error occurred during database query", e);
		}
		return true;
	}

	public Optional<Customer> getCustomerByEmail(String email){
        String sql = "select * from customers where email = ?";
        Optional<Customer> optional = Optional.empty();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	Customer customer = new Customer();
                	customer.setId(Long.parseLong(rs.getString("customer_id")));
                	customer.setName(rs.getString("name"));
                	customer.setEmail(rs.getString("email"));
                	customer.setPhone(rs.getString("phone"));
                	customer.setCpf(rs.getString("cpf"));
                	customer.setActive(rs.getBoolean("active"));
					customer.getAddress().setId(rs.getLong("address_id"));
                    optional = Optional.of(customer);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return optional;
	}

	public Optional<Customer> getCustomerById(Long customerId) {
		String sql = "select * from customers where customer_id = ?";
		Optional<Customer> optional = Optional.empty();

		try (Connection conn = dataSource.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setLong(1, customerId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Customer customer = new Customer();
					customer.setId(Long.parseLong(rs.getString("customer_id")));
					customer.setName(rs.getString("name"));
					customer.setEmail(rs.getString("email"));
					customer.setPhone(rs.getString("phone"));
					customer.setCpf(rs.getString("cpf"));
					customer.setActive(rs.getBoolean("active"));

					customer.setAddress(new Address());
					customer.getAddress().setId(rs.getLong("address_id"));

					optional = Optional.of(customer);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error occurred during database query", e);
		}
		return optional;
	}

	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<>();

		String sql = "select * from customers";
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getLong("customer_id"));
				customer.setName(rs.getString("name"));
				customer.setPhone(rs.getString("phone"));
				customer.setEmail(rs.getString("email"));
				customer.setCpf(rs.getString("cpf"));
				customer.setActive(rs.getBoolean("active"));

				customer.setAddress(new Address());
				customer.getAddress().setId(rs.getLong("address_id"));

				customers.add(customer);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error occurred during database query", e);
		}
		return customers;
	}
}
