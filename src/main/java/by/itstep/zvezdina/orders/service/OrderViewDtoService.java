package by.itstep.zvezdina.orders.service;

import by.itstep.zvezdina.orders.dto.OrderViewDto;
import by.itstep.zvezdina.orders.utils.ConnectionUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderViewDtoService {

    private static final String FIND_ALL_QUERY = "SELECT orders.order_id, customers.last_name, orders.order_date, " +
                        "order_statuses.name, " +
                        "orders.comments, orders.shipped_date, shippers.name " +
                        "FROM orders LEFT JOIN customers ON orders.customer_id = customers.customer_id " +
                        "LEFT JOIN shippers ON orders.shipper_id = shippers.shipper_id " +
                        "LEFT JOIN order_statuses ON orders.status = order_statuses.order_status_id;";

    private static final String FIND_ALL_BY_CUSTOMER_ID_QUERY = "SELECT orders.order_id, customers.last_name, " +
            "orders.order_date, " +
            "order_statuses.name, " +
            "orders.comments, orders.shipped_date, shippers.name " +
            "FROM orders LEFT JOIN customers ON orders.customer_id = customers.customer_id " +
            "LEFT JOIN shippers ON orders.shipper_id = shippers.shipper_id " +
            "LEFT JOIN order_statuses ON orders.status = order_statuses.order_status_id " +
            "WHERE customers.customer_id = ?;";

    private static final String FIND_PAGE_QUERY = "SELECT orders.order_id, customers.last_name, orders.order_date, " +
            "order_statuses.name, orders.comments, orders.shipped_date, shippers.name " +
            "FROM orders LEFT JOIN customers ON orders.customer_id = customers.customer_id " +
                        "LEFT JOIN shippers ON orders.shipper_id = shippers.shipper_id " +
                        "LEFT JOIN order_statuses ON orders.status = order_statuses.order_status_id " +
            "LIMIT ?, ?;";

    private static final int NUM_OF_ELEMENTS = 10;

    public List<OrderViewDto> findAll() {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_ALL_QUERY);
            ResultSet rs = pstmt.executeQuery()) {

            List<OrderViewDto> foundOrderViewsDto = new ArrayList<>();
            while (rs.next()) {
                OrderViewDto orderViewDto = new OrderViewDto();
                orderViewDto.setOrderId(rs.getInt("order_id"));
                orderViewDto.setCustomerLastName(rs.getString("last_name"));
                orderViewDto.setOrderDate(rs.getDate("order_date"));
                orderViewDto.setOrderStatus(rs.getString("order_statuses.name"));
                orderViewDto.setComments(rs.getString("comments"));
                orderViewDto.setShippedDate(rs.getDate("shipped_date"));
                orderViewDto.setShipperName(rs.getString("shippers.name"));
                foundOrderViewsDto.add(orderViewDto);
            }

            return foundOrderViewsDto;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding order views dto", e);
        }
    }

    public List<OrderViewDto> findAllByCustomerId(int id) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_ALL_BY_CUSTOMER_ID_QUERY)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            List<OrderViewDto> foundOrderViewsDto = new ArrayList<>();
            while (rs.next()) {
                OrderViewDto orderViewDto = new OrderViewDto();
                orderViewDto.setOrderId(rs.getInt("order_id"));
                orderViewDto.setCustomerLastName(rs.getString("last_name"));
                orderViewDto.setOrderDate(rs.getDate("order_date"));
                orderViewDto.setOrderStatus(rs.getString("order_statuses.name"));
                orderViewDto.setComments(rs.getString("comments"));
                orderViewDto.setShippedDate(rs.getDate("shipped_date"));
                orderViewDto.setShipperName(rs.getString("shippers.name"));
                foundOrderViewsDto.add(orderViewDto);
            }

            return foundOrderViewsDto;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding order views dto", e);
        }
    }

    public List<OrderViewDto> getPage(int page) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_PAGE_QUERY)) {
            pstmt.setInt(1, NUM_OF_ELEMENTS * (page - 1));
            pstmt.setInt(2, NUM_OF_ELEMENTS);

            try(ResultSet rs = pstmt.executeQuery()) {
                List<OrderViewDto> foundOrderViewsDto = new ArrayList<>();
                while (rs.next()) {
                    OrderViewDto orderViewDto = new OrderViewDto();
                    orderViewDto.setOrderId(rs.getInt("order_id"));
                    orderViewDto.setCustomerLastName(rs.getString("last_name"));
                    orderViewDto.setOrderDate(rs.getDate("order_date"));
                    orderViewDto.setOrderStatus(rs.getString("order_statuses.name"));
                    orderViewDto.setComments(rs.getString("comments"));
                    orderViewDto.setShippedDate(rs.getDate("shipped_date"));
                    orderViewDto.setShipperName(rs.getString("shippers.name"));
                    foundOrderViewsDto.add(orderViewDto);
                }
                return foundOrderViewsDto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while getting order page", e);
        }
    }
}
