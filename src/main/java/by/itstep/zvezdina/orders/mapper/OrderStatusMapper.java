package by.itstep.zvezdina.orders.mapper;

import by.itstep.zvezdina.orders.entity.OrderStatus;


import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderStatusMapper {

    public OrderStatus extract(ResultSet rs) throws SQLException {

        int orderStatusId = rs.getInt("order_status_id");
        String name = rs.getString("name");

        OrderStatus orderStatus = OrderStatus.getBuilder()
                                             .setOrderStatusId(orderStatusId)
                                             .setName(name)
                                             .build();
        return orderStatus;
    }
}
