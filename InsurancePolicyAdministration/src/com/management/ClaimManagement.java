package com.management;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.model.Claim;

public class ClaimManagement {

    // Add claim record
    public boolean addClaim(List<Claim> claims) throws SQLException {
        String query = "INSERT INTO Claim VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnectionManager.getConnection()) {
            con.setAutoCommit(false);  // start transaction
            try (PreparedStatement ps = con.prepareStatement(query)) {
                for (Claim claim : claims) {
                    ps.setString(1, claim.getClaimId());
                    ps.setString(2, claim.getCustomerId());
                    ps.setString(3, claim.getAllocationId());
                    ps.setString(4, claim.getClaimType());
                    ps.setDouble(5, claim.getClaimAmount());
                    ps.setDate(6, claim.getClaimDate());
                    int result = ps.executeUpdate();
                    if (result <= 0) {
                        con.rollback();
                        return false;
                    }
                }
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
            return true;
        }
    }

    // Check total number of claim records
    public int checkExists() {
        String query = "SELECT COUNT(*) FROM Claim";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // View all claim records
    public List<Claim> viewData() throws SQLException {
        List<Claim> claimList = new ArrayList<>();
        String query = "SELECT * FROM Claim";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Claim c = new Claim(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getDate(6)
                );
                claimList.add(c);
            }
        }
        return claimList;
    }

    // Delete claim by ID
    public boolean delete(String claimId) throws SQLException {
        String query = "DELETE FROM Claim WHERE CLAIM_ID = ?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, claimId);
            return ps.executeUpdate() > 0;
        }
    }
}
