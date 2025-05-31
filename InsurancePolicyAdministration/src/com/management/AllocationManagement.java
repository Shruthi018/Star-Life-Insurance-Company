package com.management;

import com.model.Allocation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.exception.*;

public class AllocationManagement {

    public boolean addAllocation(List<Allocation> list) throws SQLException {
        if (list == null || list.isEmpty()) return false;
        String query = "INSERT INTO Allocation VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnectionManager.getConnection()) {
            for (Allocation alloc : list) {
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, alloc.getAllocationId());
                    ps.setString(2, alloc.getCustomerId());
                    ps.setString(3, alloc.getPolicyId());
                    ps.setString(4, alloc.getNomineeName());
                    ps.setDouble(5, alloc.getSumAssured());
                    ps.setInt(6, alloc.getNoOfYears());
                    ps.setDouble(7, alloc.getPremiumAmount());
                    ps.setString(8, alloc.getPremiumPaymentCycle());
                    ps.setDouble(9, alloc.getTotalPayment());
                    ps.setString(10, alloc.getPolicyStatus());
                    ps.executeUpdate();
                }
            }
            return true;
        }
    }

    public boolean updateTotalPayment(String allocationId, double totalPayment) throws SQLException {
        String query = "UPDATE Allocation SET TOTAL_PAYMENT = ? WHERE ALLOCATION_ID = ?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDouble(1, totalPayment);
            ps.setString(2, allocationId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updatePolicyStatus(String allocationId, String status) throws SQLException {
        String query = "UPDATE Allocation SET POLICY_STATUS = ? WHERE ALLOCATION_ID = ?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setString(2, allocationId);
            return ps.executeUpdate() > 0;
        }
    }
    public void updateStatusClaim(String allocationId, String status, double updatedSum) throws SQLException {
        String query = "UPDATE Allocation SET POLICY_STATUS = ?, SUM_ASSURED = ? WHERE ALLOCATION_ID = ?";
        try (PreparedStatement ps = DBConnectionManager.getConnection().prepareStatement(query)) {
            ps.setString(1, status);
            ps.setDouble(2, updatedSum);
            ps.setString(3, allocationId);
            ps.executeUpdate();
        }
    }

    public Allocation getAllocationById(String allocationId) throws SQLException {
        String query = "SELECT * FROM Allocation WHERE ALLOCATION_ID = ?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, allocationId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractAllocation(rs);
            }
        }
        return null;
    }

    public List<Allocation> getAllocationsByPolicyId(String policyId) throws SQLException {
        String query = "SELECT * FROM Allocation WHERE POLICY_ID = ?";
        List<Allocation> list = new ArrayList<>();
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, policyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractAllocation(rs));
            }
        }
        return list;
    }

    public List<Allocation> getAllocationsByPolicyStatus(String status) throws SQLException {
        String query = "SELECT * FROM Allocation WHERE POLICY_STATUS = ?";
        List<Allocation> list = new ArrayList<>();
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractAllocation(rs));
            }
        }
        return list;
    }

    private Allocation extractAllocation(ResultSet rs) throws SQLException {
        return new Allocation(
            rs.getString("ALLOCATION_ID"),
            rs.getString("CUSTOMER_ID"),
            rs.getString("POLICY_ID"),
            rs.getString("NOMINEE_NAME"),
            rs.getDouble("SUM_ASSURED"),
            rs.getInt("NO_OF_YEARS"),
            rs.getDouble("PREMIUM_AMOUNT"),
            rs.getString("PREMIUM_PAYMENT_CYCLE"),
            rs.getDouble("TOTAL_PAYMENT"),
            rs.getString("POLICY_STATUS")
        );
    }
    public boolean deleteAllocation(String allocationId) throws SQLException, InvalidAllocationException {
        if (allocationId == null || allocationId.trim().isEmpty()) {
            throw new InvalidAllocationException("Invalid allocation ID provided.");
        }

        String query = "DELETE FROM Allocation WHERE ALLOCATION_ID = ?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, allocationId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public List<Allocation> getAllocationsByStatus(String status) throws SQLException {
        String query = "SELECT * FROM Allocation WHERE POLICY_STATUS = ?";
        List<Allocation> list = new ArrayList<>();
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractAllocation(rs));
            }
        }
        return list;
    }
    public List<Allocation> dataRetrieval(String allocationId) throws SQLException {
        List<Allocation> allocations = new ArrayList<>();
        String query = "SELECT * FROM Allocation WHERE ALLOCATION_ID = ?";

        try (PreparedStatement ps = DBConnectionManager.getConnection().prepareStatement(query)) {
            ps.setString(1, allocationId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Allocation allocation = new Allocation(
                    rs.getString("ALLOCATION_ID"),
                    rs.getString("CUSTOMER_ID"),
                    rs.getString("POLICY_ID"),
                    rs.getString("NOMINEE_NAME"),
                    rs.getDouble("SUM_ASSURED"),
                    rs.getInt("NO_OF_YEARS"),
                    rs.getDouble("PREMIUM_AMOUNT"),
                    rs.getString("PREMIUM_PAYMENT_CYCLE"),
                    rs.getDouble("TOTAL_PAYMENT"),
                    rs.getString("POLICY_STATUS")
                );
                allocations.add(allocation);
            }
        }
        return allocations;
    }
} 
