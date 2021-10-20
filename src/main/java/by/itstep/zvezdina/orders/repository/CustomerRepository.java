package by.itstep.zvezdina.orders.repository;

import by.itstep.zvezdina.orders.entity.Customer;
import by.itstep.zvezdina.orders.mapper.CustomerMapper;
import by.itstep.zvezdina.orders.utils.ConnectionUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class CustomerRepository {

    private final CustomerMapper customerMapper = new CustomerMapper();
    private static final Logger logger = Logger.getLogger(CustomerRepository.class.getName());

    private static final String INSERT_QUERY =
            "INSERT INTO customers(first_name, last_name, birth_date, phone, address, city, state, points, email, password) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String FIND_BY_ID_QUERY = "SELECT * " +
                                                     "FROM customers " +
                                                     "WHERE customer_id = ?;";

    private static final String FIND_ALL_QUERY = "SELECT * " +
                                                   "FROM customers;";

    private static final String FIND_BY_STATE_QUERY = "SELECT * " +
                                                        "FROM customers " +
                                                        "WHERE state = ?;";

    private static final String UPDATE_QUERY = "UPDATE customers " +
                                                "SET first_name = ?, last_name = ?, birth_date = ?, phone = ?, " +
                                                    "address = ?, city = ?, state = ?, points = ?, email = ?, password = ? " +
                                                "WHERE customer_id = ?;";

    private static final String DELETE_BY_ID_QUERY = "DELETE FROM customers " +
                                                     "WHERE customer_id = ?;";

    private static final String FIND_PAGE_QUERY = "SELECT * FROM customers " +
                                                  "LIMIT ?, ?;";

    private static final int NUM_OF_ELEMENTS = 10;

    public void create(Customer customer) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY)) {

            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setDate(3, customer.getBirthDate());
            pstmt.setString(4, customer.getPhone());
            pstmt.setString(5, customer.getAddress());
            pstmt.setString(6, customer.getCity());
            pstmt.setString(7, customer.getState());
            pstmt.setInt(8, customer.getPoints());
            pstmt.setString(9, customer.getEmail());
            pstmt.setString(10, customer.getPassword());

            int savedRows = pstmt.executeUpdate();
            logger.info(savedRows + " customer(s) was saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while saving customer", e);
        }
    }

    public Customer findById(int id) {
        try(Connection con = ConnectionUtils.getConnection();
        PreparedStatement pstmt = con.prepareStatement(FIND_BY_ID_QUERY)) {

            pstmt.setInt(1, id);

            try(ResultSet rs = pstmt.executeQuery()) {

                Customer found = null;
                if (rs.next()) {
                    found = customerMapper.extract(rs);
                    logger.info("Found customer: " + found);
                }

                if (found == null) {
                    logger.info("Customer with id " + id + " wasn't found!");
                }
                return found;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding customer", e);
        }
    }

    public List<Customer> findAll() {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_ALL_QUERY);
            ResultSet rs = pstmt.executeQuery()) {

            List<Customer> foundCustomers = new ArrayList<>();
            while (rs.next()) {
                Customer customer = customerMapper.extract(rs);
                foundCustomers.add(customer);
            }

            logger.info("Found customers: " + foundCustomers.size());
            return foundCustomers;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding customers", e);
        }
    }

    public void update(Customer customer) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY)) {

            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setDate(3, customer.getBirthDate());
            pstmt.setString(4, customer.getPhone());
            pstmt.setString(5, customer.getAddress());
            pstmt.setString(6, customer.getCity());
            pstmt.setString(7, customer.getState());
            pstmt.setInt(8, customer.getPoints());
            pstmt.setString(9, customer.getEmail());
            pstmt.setString(10, customer.getPassword());
            pstmt.setInt(11, customer.getCustomerId());

            int updatedRows = pstmt.executeUpdate();
            logger.info(updatedRows + " customer(s) was updated.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating customer", e);
        }
    }

    public void deleteById(int id) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(DELETE_BY_ID_QUERY)) {

            pstmt.setInt(1, id);
            int deletedRows = pstmt.executeUpdate();
            logger.info(deletedRows + " customer(s) was deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while deleting customer", e);
        }
    }

    public List<Customer> getPage(int page) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_PAGE_QUERY)) {
            pstmt.setInt(1, NUM_OF_ELEMENTS * (page - 1));
            pstmt.setInt(2, NUM_OF_ELEMENTS);
            try(ResultSet rs = pstmt.executeQuery()) {
                List<Customer> customers = new ArrayList<>();
                while (rs.next()) {
                    Customer customer = customerMapper.extract(rs);
                    customers.add(customer);
                }
                return customers;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while getting customer page", e);
        }
    }

}
