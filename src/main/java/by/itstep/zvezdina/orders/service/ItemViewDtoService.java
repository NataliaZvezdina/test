package by.itstep.zvezdina.orders.service;

import by.itstep.zvezdina.orders.dto.ItemViewDto;
import by.itstep.zvezdina.orders.utils.ConnectionUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemViewDtoService {

//    private static final String FIND_ALL_BY_ORDER_ID_QUERY = "SELECT products.product_id, products.name, " +
//                                                                "order_items.quantity, order_items.unit_price " +
//                                                             "FROM order_items LEFT JOIN products " +
//                                                             "ON order_items.product_id = products.product_id " +
//                                                             "WHERE order_items.order_id = ?;";

    private static final String FIND_ALL_BY_ORDER_ID_QUERY = "SELECT p.product_id, p.name, oi.quantity, " +
            "oi.unit_price, (oi.quantity * oi.unit_price) AS 'total_price' " +
            "FROM order_items oi LEFT JOIN products p " +
            "ON oi.product_id = p.product_id " +
            "WHERE oi.order_id = ?;";

    public List<ItemViewDto> findAllByOrderId(int orderId) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_ALL_BY_ORDER_ID_QUERY)) {

            pstmt.setInt(1, orderId);

            try(ResultSet rs = pstmt.executeQuery()) {

                List<ItemViewDto> foundOrderItemsDto = new ArrayList<>();
                while (rs.next()) {
                    ItemViewDto itemViewDto = new ItemViewDto();
                    itemViewDto.setProductName(rs.getString("name"));
                    itemViewDto.setQuantity(rs.getInt("quantity"));
                    itemViewDto.setUnitPrice(rs.getDouble("unit_price"));
                    itemViewDto.setTotalPrice(rs.getDouble("total_price"));
                    foundOrderItemsDto.add(itemViewDto);
                }
                return foundOrderItemsDto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding order item dto", e);
        }
    }
}
