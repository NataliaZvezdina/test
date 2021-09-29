package by.itstep.zvezdina.orders.mapper;

import by.itstep.zvezdina.orders.entity.Order;


import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper {

    public Order extract(ResultSet rs) throws SQLException {
        return Order.getBuilder()
                .setOrderId(rs.getInt("order_id"))
                .setCustomerId(rs.getInt("customer_id"))
                .setOrderDate(rs.getDate("order_date"))
                .setStatusId(rs.getInt("status"))
                .setComments(rs.getString("comments"))
                .setShippedDate(rs.getDate("shipped_date"))
                .setShipperId(rs.getInt("shipper_id"))
                .build();
    }
}
