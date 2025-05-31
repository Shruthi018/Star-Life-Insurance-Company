package com.management;

import java.sql.*;
import java.util.*;
import com.model.Policy;

public class PolicyManagement {

    // Utility method to get a DB connection
    private Connection getConnection() throws SQLException {
        return DBConnectionManager.getConnection();
    }

    // Adds a list of policies to the database
    public int addPolicies(List<Policy> policies) {
        String q = "INSERT INTO Policy VALUES (?, ?, ?, ?, ?, ?, ?)";
        int count = 0;
        try (Connection con = getConnection()) {
            for (Policy p : policies) {
                try (PreparedStatement s = con.prepareStatement(q)) {
                    // Set values from Policy object
                    s.setString(1, p.getPolicyId());
                    s.setInt(2, p.getSchemeNumber());
                    s.setString(3, p.getPolicyName());
                    s.setString(4, p.getPolicyType());
                    s.setInt(5, p.getMaxNoOfYears());
                    s.setDouble(6, p.getPremiumRate());
                    s.setInt(7, p.getMaxSumAssured());
                    count += s.executeUpdate();  // Update count if insertion succeeds
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // Generic method to update a single column value in Policy table by Policy ID
    public boolean updatePolicy(String column, int value, String id) {
        String q = "UPDATE Policy SET " + column + " = ? WHERE POLICY_ID = ?";
        try (Connection con = getConnection(); PreparedStatement s = con.prepareStatement(q)) {
            s.setInt(1, value);
            s.setString(2, id);
            return s.executeUpdate() == 1;  // true if exactly one row was updated
        } catch (Exception e) {
            return false;
        }
    }

    // Retrieve all policy records
    public List<Policy> getAllPolicies() {
        return getPolicies("SELECT * FROM Policy");
    }

    // Search for policies using a keyword in name or type
    public List<Policy> searchPolicies(String keyword) {
        String q = "SELECT * FROM Policy WHERE POLICY_NAME LIKE ? OR POLICY_TYPE LIKE ?";
        return getPolicies(q, "%" + keyword + "%", "%" + keyword + "%");
    }

    // Sort policies by a chosen column (premium or max years)
    public List<Policy> sortPolicies(String by) {
        String col = by.equals("premium") ? "PREMIUM_RATE" : "MAX_NO_OF_YEARS";
        return getPolicies("SELECT * FROM Policy ORDER BY " + col);
    }

    // Get a specific policy by its ID
    public Policy getPolicyById(String id) {
        List<Policy> list = getPolicies("SELECT * FROM Policy WHERE POLICY_ID = ?", id);
        return list.isEmpty() ? null : list.get(0);
    }

    // Delete a policy by ID
    public boolean deletePolicy(String id) {
        String q = "DELETE FROM Policy WHERE POLICY_ID = ?";
        try (Connection con = getConnection(); PreparedStatement s = con.prepareStatement(q)) {
            s.setString(1, id);
            return s.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    // Check if a policy ID exists
    public boolean policyExists(String id) {
        return getPolicyById(id) != null;
    }

    // Return total number of policies
    public int getPolicyCount() {
        String q = "SELECT COUNT(*) FROM Policy";
        try (Connection con = getConnection(); PreparedStatement s = con.prepareStatement(q); ResultSet rs = s.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    // Reusable method to run a query and return list of Policy objects
    private List<Policy> getPolicies(String query, String... params) {
        List<Policy> list = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement s = con.prepareStatement(query)) {
            // Set parameters if any
            for (int i = 0; i < params.length; i++)
                s.setString(i + 1, params[i]);
            
            try (ResultSet rs = s.executeQuery()) {
                while (rs.next()) {
                    // Construct Policy object from DB result
                    Policy policy = new Policy(
                        rs.getString(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getDouble(6), rs.getInt(7)
                    );
                    list.add(policy);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}