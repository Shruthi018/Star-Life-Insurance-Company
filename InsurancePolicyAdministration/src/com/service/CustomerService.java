package com.service;

import com.management.CustomerManagement;
import com.model.Customer;
//import com.util.ApplicationUtil;

import java.util.List;
import java.util.regex.Pattern;

public class CustomerService {

    // Add customer with ID generation and validation
    public boolean addCustomer(Customer customer) {
        if (!validateCustomer(customer)) {
            System.out.println("Validation failed: Invalid customer data.");
            return false;
        }

        return CustomerManagement.addCustomer(customer);
    }

    // Update full customer info with validation
    public boolean updateCustomer(Customer customer) {
        if (!validateCustomer(customer)) {
            System.out.println("Validation failed: Invalid customer data.");
            return false;
        }
        return CustomerManagement.updateCustomer(customer);
    }

    // Update only phone number
    public boolean updatePhoneNumber(String customerId, long phoneNumber) {
        if (customerId == null || customerId.isEmpty() || phoneNumber <= 0) {
            System.out.println("Invalid customer ID or phone number.");
            return false;
        }
        return CustomerManagement.updatePhoneNumber(customerId, phoneNumber);
    }

    // Update only email ID
    public boolean updateEmailId(String customerId, String emailId) {
        if (customerId == null || customerId.isEmpty() || !isValidEmail(emailId)) {
            System.out.println("Invalid customer ID or email.");
            return false;
        }
        return CustomerManagement.updateEmailId(customerId, emailId);
    }

    // Delete customer
    public boolean deleteCustomer(String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            System.out.println("Invalid customer ID.");
            return false;
        }
        return CustomerManagement.deleteCustomerById(customerId);
    }

    // Get customer by ID
    public Customer getCustomer(String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            System.out.println("Invalid customer ID.");
            return null;
        }
        return CustomerManagement.getCustomerById(customerId);
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return CustomerManagement.getAllCustomers();
    }

    // Validate customer data
    private boolean validateCustomer(Customer customer) {
        if (customer == null) return false;

        if (customer.getCustomerName() == null || customer.getCustomerName().trim().isEmpty()) return false;
        if (customer.getAge() <= 0) return false;
        if (customer.getPhoneNumber() <= 0) return false;
        if (!isValidEmail(customer.getEmailId())) return false;
        return true;
    }

    // Simple email validation
    private boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,}$";
        return Pattern.matches(emailRegex, email.toLowerCase());
    }
}
