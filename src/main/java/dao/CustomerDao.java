package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import model.Customer;

public class CustomerDao {
	private DataSource dataSource;

	public CustomerDao(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	public Boolean save(Customer customer){
		Optional<Customer> optional = getClientByEmail(customer.getEmail());
		if(optional.isPresent()) {
			return false;
		}
        String sql = "insert into customers (name, email, phone, cpf, active) values (?, ?, ?, ?, ?)";
		try(Connection conn = dataSource.getConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getCpf());
            ps.setBoolean(5, customer.getActive());
            
			ps.executeUpdate();
		}catch (SQLException e) {
			throw new RuntimeException("Erro durante a escrita no BD", e);
		}
		return true;
	}
	
	public Optional<Customer> getClientByEmail(String email){
        String sql = "select customer_id, name, email, phone, cpf, active from customers where email = ?";
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
                    optional = Optional.of(customer);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro durante a consulta no BD", e);
        }
        return optional;
	}
}
