package dao;

import model.Address;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddressDao {
    private DataSource dataSource;

    public AddressDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long save(Address address) {
        Optional<Address> optional = getAddressById(address.getId());
        if(optional.isPresent()) {
            return 0L;
        }
        String sql = "insert into addresses (street, number, complement, neighborhood, cep, city, state) values (?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, address.getStreet());
            ps.setString(2, address.getNumber());
            ps.setString(3, address.getComplement());
            ps.setString(4, address.getNeighborhood());
            ps.setString(5, address.getCep());
            ps.setString(6, address.getCity());
            ps.setString(7, address.getState());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return 0L;
    }

    public Optional<Address> getAddressById(Long id){
        String sql = "select * from addresses where address_id = ?";
        Optional<Address> optional = Optional.empty();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, String.valueOf(id));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Address address = new Address();
                    address.setId(Long.parseLong(rs.getString("id")));
                    address.setStreet(rs.getString("street"));
                    address.setNumber(rs.getString("number"));
                    address.setComplement(rs.getString("complement"));
                    address.setNeighborhood(rs.getString("neighborhood"));
                    address.setCep(rs.getString("cep"));
                    address.setCity(rs.getString("city"));
                    address.setState(rs.getString("state"));
                    optional = Optional.of(address);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }
        return optional;
    }

    public List<Address> getAllAddresses() {
        List<Address> addresses = new ArrayList<>();
        String sql = "select * from addresses";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Address address = new Address();
                address.setId(rs.getLong("address_id"));
                address.setStreet(rs.getString("street"));
                address.setNumber(rs.getString("number"));
                address.setComplement(rs.getString("complement"));
                address.setNeighborhood(rs.getString("neighborhood"));
                address.setCep(rs.getString("cep"));
                address.setCity(rs.getString("city"));
                address.setState(rs.getString("state"));
                addresses.add(address);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred during database query", e);
        }

        return addresses;
    }
}
