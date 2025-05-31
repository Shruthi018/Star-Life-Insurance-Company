package com.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.model.Payment;

public class PaymentManagement {

    // Check if there are any payments in the database
    public int checkexists() {
        int count = 0;
        try (Connection con = DBConnectionManager.getConnection()) {
            String query = "SELECT COUNT(*) FROM Payment";
            PreparedStatement p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // Add multiple payments to the database
    public boolean addPayment(List<Payment> list) throws ClassNotFoundException {
        Connection con = null;
        try {
            con = DBConnectionManager.getConnection();
            con.setAutoCommit(false); // Begin transaction
            
            String query = "INSERT INTO Payment (PAYMENT_ID, CUSTOMER_ID, ALLOCATION_ID, PREMIUM, PAYMENT_DATE, PAYMENT_MODE, INSTALLMENT_COUNT) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            for (Payment x : list) {
                try (PreparedStatement p = con.prepareStatement(query)) {
                    p.setString(1, x.getPaymentId());
                    p.setString(2, x.getCustomerId());
                    p.setString(3, x.getAllocationId());
                    p.setDouble(4, x.getPremium());
                    p.setDate(5, x.getPaymentDate());
                    p.setString(6, x.getPaymentMode());
                    p.setInt(7, x.getInstallmentCount());
                    
                    p.executeUpdate();
                }
            }
            con.commit(); // Commit transaction after all inserts
            return true;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback transaction in case of error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true); // Reset auto-commit
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    // Retrieve all payments from the database
    public List<Payment> viewData() throws SQLException {
        List<Payment> Plist = new ArrayList<>();
        try (Connection con = DBConnectionManager.getConnection()) {
            String query = "SELECT * FROM Payment";
            PreparedStatement p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                String paymentId = rs.getString("PAYMENT_ID");
                String customerId = rs.getString("CUSTOMER_ID");
                String allocationId = rs.getString("ALLOCATION_ID");
                double premium = rs.getDouble("PREMIUM");
                java.sql.Date paymentDate = rs.getDate("PAYMENT_DATE");
                String paymentMode = rs.getString("PAYMENT_MODE");
                int installmentCount = rs.getInt("INSTALLMENT_COUNT");

                Payment pobj = new Payment(paymentId, customerId, allocationId, premium, paymentDate, paymentMode, installmentCount);
                Plist.add(pobj);
            }
        }
        return Plist;
    }

    // Delete a payment by its ID
    public boolean delete(String paymentId) throws SQLException {
        try (Connection con = DBConnectionManager.getConnection()) {
            String query = "DELETE FROM Payment WHERE PAYMENT_ID = ?";
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1, paymentId);
            int rows = p.executeUpdate();
            return rows > 0;
        }
    }
}
