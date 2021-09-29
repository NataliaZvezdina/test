package by.itstep.zvezdina.orders.repository;

import by.itstep.zvezdina.orders.entity.OrderItem;
import by.itstep.zvezdina.orders.mapper.OrderItemMapper;
import by.itstep.zvezdina.orders.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OrderItemRepository {

    private final OrderItemMapper orderItemMapper = new OrderItemMapper();
    private static final Logger logger = Logger.getLogger(OrderItemRepository.class.getName());

    private static final String INSERT_QUERY = "INSERT INTO order_items(order_id, product_id, quantity, " +
                                                   "unit_price) " +
                                               "VALUES(?, ?, ?, ?);";

    private static final String FIND_BY_ORDER_ID_QUERY = "SELECT * " +
                                                         "FROM order_items " +
                                                         "WHERE order_id = ?;";

    private static final String FIND_BY_PRODUCT_ID_QUERY = "SELECT * " +
                                                           "FROM order_items " +
                                                           "WHERE product_id = ?;";

    private static final String FIND_ALL_QUERY = "SELECT * " +
                                                 "FROM order_items;";

    private static final String UPDATE_QUERY = "UPDATE order_items " +
                                               "SET quantity = ?, unit_price = ?" +
                                               "WHERE order_id = ? AND product_id = ?;";

    private static final String DELETE_BY_ORDER_ID_QUERY = "DELETE FROM order_items " +
                                                           "WHERE order_id = ?;";

    private static final String DELETE_BY_PRODUCT_ID_QUERY = "DELETE FROM order_items " +
                                                             "WHERE product_id = ?;";

    public void create(OrderItem orderItem) {
        try(Connection con =  ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY)) {

            pstmt.setInt(1, orderItem.getOrderId());
            pstmt.setInt(2, orderItem.getProductId());
            pstmt.setInt(3, orderItem.getQuantity());
            pstmt.setDouble(4, orderItem.getUnitPrice());

            int savedRows = pstmt.executeUpdate();
            logger.info(savedRows + " order item was saved successfully: " + orderItem);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while saving order item!", e);
        }
    }

    public List<OrderItem> findByOrderId(int orderId) {

        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_BY_ORDER_ID_QUERY)) {

            pstmt.setInt(1, orderId);

            try(ResultSet rs = pstmt.executeQuery()) {
                List<OrderItem> foundOrderItems = new ArrayList<>();
                while(rs.next()) {
                    foundOrderItems.add(orderItemMapper.extract(rs));
                }
                logger.info("Found order items: " + foundOrderItems.size());
                return foundOrderItems;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding order items", e);
        }
    }

    public List<OrderItem> findByProductId(int productId) {

        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_BY_PRODUCT_ID_QUERY)) {

            pstmt.setInt(1, productId);

            try(ResultSet rs = pstmt.executeQuery()) {
                List<OrderItem> foundOrderItems = new ArrayList<>();
                while(rs.next()) {
                    foundOrderItems.add(orderItemMapper.extract(rs));
                }
                logger.info("Found order items: " + foundOrderItems.size());
                return foundOrderItems;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding order items", e);
        }
    }

    public List<OrderItem> findAll() {

        try (Connection con = ConnectionUtils.getConnection();
             PreparedStatement pstmt = con.prepareStatement(FIND_ALL_QUERY);
             ResultSet rs = pstmt.executeQuery()) {

            List<OrderItem> foundOrderItems = new ArrayList<>();
            while (rs.next()) {
                foundOrderItems.add(orderItemMapper.extract(rs));
            }

            logger.info("Found order items: " + foundOrderItems.size());
            return foundOrderItems;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding order items", e);
        }
    }

    public void update(OrderItem orderItem) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY)) {

            pstmt.setInt(1, orderItem.getQuantity());
            pstmt.setDouble(2, orderItem.getUnitPrice());
            pstmt.setInt(3, orderItem.getOrderId());
            pstmt.setInt(4, orderItem.getProductId());

            int updatedRows = pstmt.executeUpdate();

            logger.info(updatedRows + " order item was updated successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating order item", e);
        }
    }

    public void deleteByOrderId(int orderId) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(DELETE_BY_ORDER_ID_QUERY)) {

            pstmt.setInt(1, orderId);

            int deletedRows = pstmt.executeUpdate();

            logger.info(deletedRows + " shipper(s) were deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while deleting order item", e);
        }
    }

    public void deleteByProductId(int productId) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(DELETE_BY_PRODUCT_ID_QUERY)) {

            pstmt.setInt(1, productId);

            int deletedRows = pstmt.executeUpdate();

            logger.info(deletedRows + " order item(s) were deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while deleting order item", e);
        }
    }
}
