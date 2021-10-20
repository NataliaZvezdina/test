package by.itstep.zvezdina.orders.repository;

import by.itstep.zvezdina.orders.entity.Product;
import by.itstep.zvezdina.orders.mapper.ProductMapper;
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
public class ProductRepository {

    private final ProductMapper productMapper = new ProductMapper();
    private static final Logger logger = Logger.getLogger(ProductRepository.class.getName());

    private static final String INSERT_QUERY = "INSERT INTO products(name, quantity_in_stock, unit_price) " +
                                               "VALUES(?, ?, ?);";

    private static final String FIND_BY_ID_QUERY = "SELECT * " +
                                                     "FROM products " +
                                                     "WHERE product_id = ?;";

    private static final String FIND_ALL_QUERY = "SELECT * " +
                                                   "FROM products;";

    private static final String DELETE_BY_ID_QUERY = "DELETE FROM products " +
                                                     "WHERE product_id = ?;";

    private static final String UPDATE_QUERY = "UPDATE products " +
                                               "SET name = ?, quantity_in_stock = ?, unit_price = ? " +
                                               "WHERE product_id = ?;";

    private static final String FIND_PAGE_QUERY = "SELECT * FROM products " +
                                                  "LIMIT ?, ?;";

    private static final int NUM_OF_ELEMENTS = 5;

    public void create(Product product) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY)) {

            pstmt.setString(1, product.getName());
            pstmt.setInt(2, product.getQuantityInStock());
            pstmt.setDouble(3, product.getUnitPrice());

            int savedRows = pstmt.executeUpdate();

            logger.info(savedRows + " Product was added successfully: " + product);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while saving product", e);
        }
    }

    public Product findById(int id) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_BY_ID_QUERY)) {
            pstmt.setInt(1, id);

            try(ResultSet rs = pstmt.executeQuery()) {

                Product found = null;
                if (rs.next()) {
                    found = productMapper.extract(rs);
                    logger.info(" Product with specified id was found: " + found);
                }

                if (found == null) {
                    logger.info(" Product with id " + id + " wasn't found!");
                }

                return found;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding product", e);
        }
    }

    public List<Product> findAll() {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_ALL_QUERY);
            ResultSet rs = pstmt.executeQuery()) {

            List<Product> foundProducts = new ArrayList<>();
            while (rs.next()) {
                foundProducts.add(productMapper.extract(rs));
            }

            logger.info("Found products: " + foundProducts.size());
            return foundProducts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding products", e);
        }
    }

    public void update(Product product) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY)) {

            pstmt.setString(1, product.getName());
            pstmt.setInt(2, product.getQuantityInStock());
            pstmt.setDouble(3, product.getUnitPrice());
            pstmt.setInt(4, product.getProductId());

            int updatedRows = pstmt.executeUpdate();
            logger.info(updatedRows + " product(s) was updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating product", e);
        }
    }

    public void deleteById(int id) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(DELETE_BY_ID_QUERY)) {

            pstmt.setInt(1, id);

            int deletedRows = pstmt.executeUpdate();
            logger.info(deletedRows + " product(s) was deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while deleting deleting", e);
        }
    }

    public List<Product> getPage(int page) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_PAGE_QUERY)) {
            pstmt.setInt(1, NUM_OF_ELEMENTS * (page - 1));
            pstmt.setInt(2, NUM_OF_ELEMENTS);
            try(ResultSet rs = pstmt.executeQuery()) {
                List<Product> products = new ArrayList<>();
                while (rs.next()) {
                    Product product = productMapper.extract(rs);
                    products.add(product);
                }
                return products;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while getting product page", e);
        }
    }

}
