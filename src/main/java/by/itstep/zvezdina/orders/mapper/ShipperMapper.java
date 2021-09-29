package by.itstep.zvezdina.orders.mapper;

import by.itstep.zvezdina.orders.entity.Shipper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShipperMapper {

    public Shipper extract(ResultSet rs) throws SQLException {
        return Shipper.getBuilder()
                .setShipperId(rs.getInt("shipper_id"))
                .setName(rs.getString("name"))
                .build();
    }
}
