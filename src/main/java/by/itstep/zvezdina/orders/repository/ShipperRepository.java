package by.itstep.zvezdina.orders.repository;

import by.itstep.zvezdina.orders.entity.Shipper;
import by.itstep.zvezdina.orders.mapper.ShipperMapper;
import by.itstep.zvezdina.orders.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ShipperRepository {

    private ShipperMapper shipperMapper = new ShipperMapper();
    private static final Logger logger = Logger.getLogger(ShipperRepository.class.getName());

    private static final String INSERT_QUERY = "INSERT INTO shippers (name) " +
                                               "VALUES (?);";

    private static final String FIND_BY_ID_QUERY = "SELECT * " +
                                                     "FROM shippers " +
                                                     "WHERE shipper_id = ?;";

    private static final String FIND_ALL_QUERY = "SELECT * " +
                                                   "FROM shippers;";

    private static final String UPDATE_QUERY = "UPDATE shippers " +
                                               "SET name = ?" +
                                               "WHERE shipper_id = ?;";

    private static final String DELETE_BY_ID_QUERY = "DELETE FROM shippers " +
                                                     "WHERE shipper_id = ?;";


    public void create(Shipper shipper) {
        try(Connection con =  ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY)) {

            pstmt.setString(1, shipper.getName());
            int savedRows = pstmt.executeUpdate();
            logger.info(savedRows + " shipper was saved successfully: " + shipper);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while saving shipper", e);
        }
    }


    public Shipper findById(int id) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(FIND_BY_ID_QUERY)) {

            pstmt.setInt(1, id);

            try(ResultSet rs = pstmt.executeQuery()) {

                Shipper found = null;
                if (rs.next()) {
                    found = shipperMapper.extract(rs);
                    logger.info("Found shipper: " + found);
                }

                if (found == null) {
                    logger.info("Shipper with id " + id + " wasn't found!");
                }

                return found;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding shipper", e);
        }
    }

    public List<Shipper> findAll() {
        try (Connection con = ConnectionUtils.getConnection();
             PreparedStatement pstmt = con.prepareStatement(FIND_ALL_QUERY);
             ResultSet rs = pstmt.executeQuery()) {

            List<Shipper> foundShippers = new ArrayList<>();
            while (rs.next()) {
                foundShippers.add(shipperMapper.extract(rs));
            }

            logger.info("Found shippers: " + foundShippers.size());

            return foundShippers;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding shippers", e);
        }

    }

    public void update(Shipper shipper) {
        try(Connection con = ConnectionUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY)) {

            pstmt.setString(1, shipper.getName());
            pstmt.setInt(2, shipper.getShipperId());

            int updatedRows = pstmt.executeUpdate();

            logger.info(updatedRows + " shipper was updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating shipper", e);
        }
    }

    public void deleteById(int id) {
        try(Connection con = ConnectionUtils.getConnection();
        PreparedStatement pstmt = con.prepareStatement(DELETE_BY_ID_QUERY)) {

            pstmt.setInt(1, id);
            int deletedRows = pstmt.executeUpdate();

            logger.info(deletedRows + " shipper was deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while deleting shipper", e);
        }
    }
}
