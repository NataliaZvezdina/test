package by.itstep.zvezdina.orders.mapper;

import by.itstep.zvezdina.orders.entity.Product;


import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper {

    public Product extract(ResultSet rs) throws SQLException {

        return Product.getBuilder()
                      .setProductId(rs.getInt("product_id"))
                      .setName(rs.getString("name"))
                      .setQuantityInStock(rs.getInt("quantity_in_stock"))
                      .setUnitPrice(rs.getDouble("unit_price"))
                      .build();
    }
}
