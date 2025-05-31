package com.management;
import java.sql.*;
import com.model.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerManagement {

    private static Connection conn = DBConnectionManager.getConnection();

    // Check if customer ID exists
    public static boolean isCustomerIdExists(String customerId) {
        String sql = "SELECT COUNT(*) FROM customer WHERE customer_id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Add a new customer to the database
    public static boolean addCustomer(Customer customer) {
        if (isCustomerIdExists(customer.getCustomerId())) {
            System.out.println("Customer with this ID already exists.");
            return false;
        }

        String sql = "INSERT INTO customer (customer_id, customer_name, dob, age, gender, occupation, annual_income, medical_history, address, phone_number, email_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getCustomerId());
            pstmt.setString(2, customer.getCustomerName());
            pstmt.setDate(3, customer.getDob());
            pstmt.setInt(4, customer.getAge());
            pstmt.setString(5, customer.getGender());
            pstmt.setString(6, customer.getOccupation());
            pstmt.setDouble(7, customer.getAnnualIncome());
            pstmt.setString(8, customer.getMedicalHistory());
            pstmt.setString(9, customer.getAddress());
            pstmt.setLong(10, customer.getPhoneNumber());
            pstmt.setString(11, customer.getEmailId());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update full customer details
    public static boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET customer_name=?, dob=?, age=?, gender=?, occupation=?, annual_income=?, medical_history=?, address=?, phone_number=?, email_id=? WHERE customer_id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getCustomerName());
            pstmt.setDate(2, customer.getDob());
            pstmt.setInt(3, customer.getAge());
            pstmt.setString(4, customer.getGender());
            pstmt.setString(5, customer.getOccupation());
            pstmt.setDouble(6, customer.getAnnualIncome());
            pstmt.setString(7, customer.getMedicalHistory());
            pstmt.setString(8, customer.getAddress());
            pstmt.setLong(9, customer.getPhoneNumber());
            pstmt.setString(10, customer.getEmailId());
            pstmt.setString(11, customer.getCustomerId());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update only phone number
    public static boolean updatePhoneNumber(String customerId, long phoneNumber) {
        String sql = "UPDATE customer SET phone_number=? WHERE customer_id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, phoneNumber);
            pstmt.setString(2, customerId);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update only email ID
    public static boolean updateEmailId(String customerId, String emailId) {
        String sql = "UPDATE customer SET email_id=? WHERE customer_id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emailId);
            pstmt.setString(2, customerId);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a customer by ID
    public static Customer getCustomerById(String customerId) {
        String sql = "SELECT * FROM customer WHERE customer_id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getString("customer_id"),
                        rs.getString("customer_name"),
                        rs.getDate("dob"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("occupation"),
                        rs.getDouble("annual_income"),
                        rs.getString("medical_history"),
                        rs.getString("address"),
                        rs.getLong("phone_number"),
                        rs.getString("email_id")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Delete customer by ID
    public static boolean deleteCustomerById(String customerId) {
        String sql = "DELETE FROM customer WHERE customer_id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerId);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all customers
    public static List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Customer(
                        rs.getString("customer_id"),
                        rs.getString("customer_name"),
                        rs.getDate("dob"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("occupation"),
                        rs.getDouble("annual_income"),
                        rs.getString("medical_history"),
                        rs.getString("address"),
                        rs.getLong("phone_number"),
                        rs.getString("email_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
