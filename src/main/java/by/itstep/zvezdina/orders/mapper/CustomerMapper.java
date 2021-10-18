package by.itstep.zvezdina.orders.mapper;

import by.itstep.zvezdina.orders.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper {

    public Customer extract(ResultSet rs) throws SQLException {
        return Customer.getBuilder()
                       .setCustomerId(rs.getInt("customer_id"))
                       .setFirstName(rs.getString("first_name"))
                       .setLastName(rs.getString("last_name"))
                       .setBirthDate(rs.getDate("birth_date"))
                       .setPhone(rs.getString("phone"))
                       .setAddress(rs.getString("address"))
                       .setCity(rs.getString("city"))
                       .setState(rs.getString("state"))
                       .setPoints(rs.getInt("points"))
                       .setEmail(rs.getString("email"))
                       .setPassword(rs.getString("password"))
                       .build();

    }
}
