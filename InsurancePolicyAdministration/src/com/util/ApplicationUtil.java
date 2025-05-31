package com.util;
import java.sql.*;
import com.model.*;
import com.management.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationUtil {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    private static int customerNum = getMaxIdFromDB("customer", "customer_id", "CUST");
    private static int policyNum = getMaxIdFromDB("policy", "policy_id", "POL");
    private static int allocationNum = getMaxIdFromDB("allocation", "allocation_id", "ALOC");

    private static int getMaxIdFromDB(String table, String column, String prefix) {
        int maxNum = 0;
        String sql = "SELECT MAX(" + column + ") AS max_id FROM " + table;
        try (Connection conn = DBConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String maxId = rs.getString("max_id");
                if (maxId != null && maxId.startsWith(prefix)) {
                    String numberPart = maxId.substring(prefix.length());
                    maxNum = Integer.parseInt(numberPart);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to retrieve max ID from " + table + ": " + e.getMessage());
        }
        return maxNum;
    }




    //ID Generators
    public static String generateCustomerId() {
        customerNum++;
        return "CUST" + customerNum;
    }

    public static String generatePolicyId() {
        policyNum++;
        return "POL" + policyNum;
    }


    public static String generateAllocationId() {
        allocationNum++;
        return "ALOC" + allocationNum;
    }
    public static java.sql.Date parseSqlDate(String dateStr) {
        try {
            return new java.sql.Date(sdf.parse(dateStr).getTime());
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use dd-MM-yyyy.");
            return null;
        }
    }

    public static String formatDate(java.sql.Date date) {
        return date != null ? sdf.format(date) : "";
    }


    // object Parsers from String 
    public static Customer parseCustomerFromString(String data) {
        try {
            String[] parts = data.split(":");
            String customerId = generateCustomerId();  // generate new ID here
            String name = parts[0];
            java.sql.Date dob = parseSqlDate(parts[1]);
            int age = Integer.parseInt(parts[2]);
            String gender = parts[3];
            String occupation = parts[4];
            double income = Double.parseDouble(parts[5]);
            String medical = parts[6];
            String address = parts[7];
            long phone = Long.parseLong(parts[8]);
            String email = parts[9];
            Customer cust = new Customer(customerId, name, dob, age, gender, occupation, income, medical, address, phone, email);
            return cust;
        } catch (Exception e) {
            System.out.println("Error parsing customer input: " + e.getMessage());
            return null;
        }
    }

    public static Policy parsePolicyFromString(String data) {
        try {
            String[] parts = data.split(":");
            String policyId = generatePolicyId();  // generate new policy ID here
            int schemeNo = Integer.parseInt(parts[0]);  
            String name = parts[1];
            String type = parts[2];
            int maxYears = Integer.parseInt(parts[3]);
            double rate = Double.parseDouble(parts[4]);
            int maxSum = Integer.parseInt(parts[5]);  
            Policy policy = new Policy(policyId, schemeNo, name, type, maxYears, rate, maxSum);
            return policy;
        } catch (Exception e) {
            System.out.println("Error parsing policy input: " + e.getMessage());
            return null;
        }
    }

}

//public static Date parseUtilDate(String dateStr) {
//    try {
//        return sdf.parse(dateStr);
//    } catch (ParseException e) {
//        System.out.println("Invalid date format. Please use dd-MM-yyyy.");
//        return null;
//    }
//}
//
//public static java.sql.Date parseSqlDate(String dateStr) {
//    Date utilDate = parseUtilDate(dateStr);
//    return utilDate != null ? new java.sql.Date(utilDate.getTime()) : null;
//}
//
//public static String formatDate(java.sql.Date date) {
//    return date != null ? sdf.format(date) : "";
//}