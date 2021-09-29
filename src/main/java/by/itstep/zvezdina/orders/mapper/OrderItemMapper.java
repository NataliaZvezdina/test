package by.itstep.zvezdina.orders.mapper;

import by.itstep.zvezdina.orders.entity.OrderItem;


import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemMapper {

    public OrderItem extract(ResultSet rs) throws SQLException {
        return OrderItem.getBuilder()
                .setOrderId(rs.getInt("order_id"))
                .setProductId(rs.getInt("product_id"))
                .setQuantity(rs.getInt("quantity"))
                .setUnitPrice(rs.getDouble("unit_price"))
                .build();
    }
}
