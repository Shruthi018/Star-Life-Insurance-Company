package com.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.util.*;
import com.management.*;
import com.model.Allocation;
import com.model.Claim;

public class ClaimService {

    private static int num = 100 + getExistingCount();
    private List<Claim> claimList = new ArrayList<>();
    private ClaimManagement claimManager = new ClaimManagement();
    private AllocationService allocationService = new AllocationService();
    private double claimAmount = 0;

    // Generate unique Claim ID
    public String generateClaimId() {
        num++;
        return "Claim" + num;
    }

    // Get all claims
    public List<Claim> getClaimList() {
        return claimList;
    }

    public void setClaimList(List<Claim> list) {
        this.claimList = list;
    }

    // Create claim object and add to list (now using String date)
    public void createClaim(String customerId, String allocationId, String type, String claimDate) throws SQLException {
        claimAmount = calculateClaimAmount(allocationId, type);

        java.sql.Date sqlDate = ApplicationUtil.parseSqlDate(claimDate);
        Claim claim = new Claim(generateClaimId(), customerId, allocationId, type, claimAmount, sqlDate);
        List<Claim> list = new ArrayList<>();
        list.add(claim);
        setClaimList(list);
    }

    // Add claim to database
    public double addClaim() throws SQLException {
        if (claimManager.addClaim(claimList)) {
            return claimAmount;
        }
        return 0;
    }

    // Calculate claim amount based on type
    public double calculateClaimAmount(String allocationId, String type) throws SQLException {
        List<Allocation> allocations = allocationService.dataRetrieval(allocationId);
        double sumAssured = 0;
        double totalPayment = 0;

        for (Allocation allocation : allocations) {
            sumAssured = allocation.getSumAssured();
            totalPayment = allocation.getTotalPayment();
        }

        double amount = 0;
        double updatedSum = sumAssured;
        String status = "active";

        switch (type.toLowerCase()) {
            case "death":
                amount = sumAssured;
                updatedSum = 0;
                status = "inactive";
                break;
            case "maturity":
                amount = totalPayment;
                updatedSum = 0;
                status = "inactive";
                break;
            case "survival benefit":
                amount = sumAssured * 0.05;
                updatedSum = sumAssured - amount;
                break;
            default:
                amount = 0;
        }

        allocationService.updateStatusClaim(allocationId, status, updatedSum);
        return amount;
    }

    // Get existing claim count
    public static int getExistingCount() {
        return new ClaimManagement().checkExists();
    }

    // View all claims
    public List<Claim> getData() throws SQLException {
        return claimManager.viewData();
    }

    // Delete claim by ID
    public boolean delete(String claimId) throws SQLException {
        return claimManager.delete(claimId);
    }
}