package by.itstep.zvezdina.orders.repository;

import by.itstep.zvezdina.orders.entity.OrderStatus;
import by.itstep.zvezdina.orders.mapper.OrderStatusMapper;
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
public class OrderStatusRepository {

    private final OrderStatusMapper orderStatusMapper = new OrderStatusMapper();
    private static final Logger logger = Logger.getLogger(OrderStatusRepository.class.getName());

    private static final String INSERT_QUERY = "INSERT INTO order_statuses(order_status_id, name) " +
                                               "VALUES(?, ?);";

    private static final String SELECT_MAX_ID_QUERY = "SELECT MAX(order_status_id) AS 'last_id' " +
                                                      "FROM order_statuses;";

    private static final String FIND_BY_ID_QUERY = "SELECT * " +
                                                     "FROM order_statuses " +
                                                     "WHERE order_status_id = ?;";

    private static final String FIND_ALL_QUERY = "SELECT * " +
                                                   "FROM order_statuses;";

    private static final String UPDATE_QUERY = "UPDATE order_statuses " +
                                               "SET name = ? " +
                                               "WHERE order_status_id = ?;";

    private static final String DELETE_BY_ID_QUERY = "DELETE FROM order_statuses " +
                                                     "WHERE order_status_id = ?;";

    public void create(OrderStatus orderStatus) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY)) {

            pstmt.setInt(1, findLastId() + 1);
            pstmt.setString(2, orderStatus.getName());

            int savedRows = pstmt.executeUpdate();

            logger.info(savedRows + " order status was saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while saving order status", e);
        }
    }



    public OrderStatus findById(int id) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_BY_ID_QUERY)) {

            pstmt.setInt(1, id);

            try(ResultSet rs = pstmt.executeQuery()) {

                OrderStatus found = null;
                if (rs.next()) {
                    found = orderStatusMapper.extract(rs);
                    logger.info("Found order status: " + found);
                }

                if (found == null) {
                    logger.info("entity.Order status with id " + id + " wasn't found!");
                }

                return found;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding order status", e);
        }
    }

    public List<OrderStatus> findAll() {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_ALL_QUERY);
            ResultSet rs = pstmt.executeQuery()) {

            List<OrderStatus> foundStatuses = new ArrayList<>();
            while (rs.next()) {
                OrderStatus orderStatus = orderStatusMapper.extract(rs);
                foundStatuses.add(orderStatus);
            }

            logger.info("Found order statuses: " + foundStatuses.size());
            return foundStatuses;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding order statuses", e);
        }
    }

    public void update(OrderStatus orderStatus) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY)) {

            pstmt.setString(1, orderStatus.getName());
            pstmt.setInt(2, orderStatus.getOrderStatusId());

            int updatedRows = pstmt.executeUpdate();
            logger.info(updatedRows + " order status(es) was updated");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating order status", e);
        }
    }

    public void deleteById(int id) {
        try(Connection con = ConnectionUtils.getConnection();
        PreparedStatement pstmt = con.prepareStatement(DELETE_BY_ID_QUERY)) {

            pstmt.setInt(1, id);
            int deletedRows = pstmt.executeUpdate();

            logger.info(deletedRows + " order status(es) was deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while deleting order status", e);
        }
    }

    private int findLastId() {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SELECT_MAX_ID_QUERY);
            ResultSet rs = pstmt.executeQuery()) {

            rs.next();
            int lastId = rs.getInt("last_id");
            return lastId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error", e);
        }
    }
}
