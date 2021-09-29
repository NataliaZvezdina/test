package by.itstep.zvezdina.orders.repository;

import by.itstep.zvezdina.orders.entity.Order;
import by.itstep.zvezdina.orders.mapper.OrderMapper;
import by.itstep.zvezdina.orders.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OrderRepository {

    private final OrderMapper orderMapper = new OrderMapper();
    private static final Logger logger = Logger.getLogger(OrderRepository.class.getName());

    private static final String INSERT_QUERY = "INSERT INTO orders(customer_id, order_date, " +
                                                    "status, comments, shipped_date, shipper_id) " +
                                               "VALUES(?, ?, ?, ?, ?, ?);";

    private static final String FIND_BY_ID = "SELECT * " +
                                             "FROM orders " +
                                             "WHERE order_id = ?;";

    private static final String FIND_ALL_QUERY = "SELECT * " +
                                                 "FROM orders;";

    private static final String UPDATE_QUERY = "UPDATE orders " +
                                               "SET customer_id = ?, order_date = ?, status = ?, comments = ?, " +
                                                    "shipped_date = ?, shipper_id = ? " +
                                               "WHERE order_id = ?;";

    private static final String DELETE_BY_ID_QUERY = "DELETE FROM orders " +
                                                     "WHERE order_id = ?;";

    public void create(Order order) {
        try(Connection con =  ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY)) {

            pstmt.setInt(1, order.getCustomerId());
            pstmt.setDate(2, order.getOrderDate());
            pstmt.setInt(3, order.getStatusId());
            pstmt.setString(4, order.getComments());
            pstmt.setDate(5, order.getShippedDate());
            pstmt.setInt(6, order.getShipperId());

            int savedRows = pstmt.executeUpdate();

            logger.info(savedRows + " order was saved successfully: " + order);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while saving order", e);
        }
    }

    public Order findById(int id) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_BY_ID)){

            pstmt.setInt(1, id);

            try(ResultSet rs = pstmt.executeQuery()) {
                Order foundOrder = null;
                if(rs.next()) {
                    foundOrder = orderMapper.extract(rs);
                }
                logger.info("Found order: " + foundOrder);
                return foundOrder;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding order", e);
        }
    }

    public List<Order> findAll() {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_ALL_QUERY);
            ResultSet rs = pstmt.executeQuery()) {

            List<Order> foundOrders = new ArrayList<>();
            while (rs.next()) {
                foundOrders.add(orderMapper.extract(rs));
            }

            logger.info("Found orders: " + foundOrders.size());

            return foundOrders;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding orders", e);
        }
    }

    public void update(Order order) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY)) {

            pstmt.setInt(1, order.getCustomerId());
            pstmt.setDate(2, order.getOrderDate());
            pstmt.setInt(3, order.getStatusId());
            pstmt.setString(4, order.getComments());
            pstmt.setDate(5, order.getShippedDate());
            pstmt.setInt(6, order.getShipperId());
            pstmt.setInt(7, order.getOrderId());

            int updatedRows = pstmt.executeUpdate();

            logger.info(updatedRows + " order was updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating order", e);
        }
    }

    public void deleteById(int id) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(DELETE_BY_ID_QUERY)) {

            pstmt.setInt(1, id);
            int deletedRows = pstmt.executeUpdate();

            logger.info(deletedRows + " order was deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while deleting order", e);
        }
    }
}
