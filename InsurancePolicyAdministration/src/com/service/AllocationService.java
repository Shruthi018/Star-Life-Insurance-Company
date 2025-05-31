package com.service;

import com.management.AllocationManagement;
import com.management.PolicyManagement;
import com.model.Allocation;
import com.model.Policy;
import com.util.ApplicationUtil;
import com.exception.InvalidAllocationException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllocationService {

    private List<Allocation> allocList = new ArrayList<>();
    private final PolicyManagement policyManager = new PolicyManagement();
    private final AllocationManagement allocManager = new AllocationManagement();

    public List<Allocation> getAllocList() {
        return allocList;
    }

    public void setAllocList(List<Allocation> list) {
        this.allocList = list;
    }
 
    public boolean createAllocation(Allocation alloc) throws SQLException, InvalidAllocationException {
        if (alloc == null) {
            throw new InvalidAllocationException("Invalid allocation data.");
        }

        List<Allocation> list = new ArrayList<>();
        list.add(alloc);
        return allocManager.addAllocation(list);
    }

    public void calculatePremium(String customerId, String policyId, String nomineeName, int cycle) throws InvalidAllocationException {
        List<Policy> policies = policyManager.getAllPolicies();
        double sumAssured = 0;
        double rate = 0;
        String status = "Active";
        String cycleType = "";
        int years = 0;
        double premium = 0;

        boolean policyFound = false;
        for (Policy p : policies) {
            if (p.getPolicyId().equalsIgnoreCase(policyId)) {
                sumAssured = p.getMaxSumAssured();
                rate = p.getPremiumRate();
                years = p.getMaxNoOfYears();
                policyFound = true;
                break;
            }
        }

        if (!policyFound) {
            throw new InvalidAllocationException("Policy with ID " + policyId + " not found.");
        }

        double amount = sumAssured * (rate/100);
        double totalPayment = 0.0;
        if (cycle == 1) {
            cycleType = "Half-year";
            premium = amount /2;
            totalPayment = premium * years * 2;
        } else if (cycle == 2) {
            cycleType = "Yearly";
            premium = amount;
            totalPayment = premium * years;
        } else {
            throw new InvalidAllocationException("Invalid payment cycle. Allowed: 1 (Half-year), 2 (Yearly).");
        }

        String allocationId = ApplicationUtil.generateAllocationId();

        Allocation alloc = new Allocation(
        	    allocationId, customerId, policyId, nomineeName, sumAssured, years,
        	    premium, cycleType, totalPayment, status);


        allocList.clear();
        allocList.add(alloc);
    }

    public boolean saveAllocation() throws SQLException, InvalidAllocationException {
        if (allocList == null || allocList.isEmpty()) {
            throw new InvalidAllocationException("No allocation data to save.");
        }
        return allocManager.addAllocation(allocList);
    }

    public boolean isValidId(String id) throws SQLException {
        Allocation alloc = allocManager.getAllocationById(id);
        return alloc != null;
    }

    public boolean deleteAllocation(String allocationId) throws SQLException, InvalidAllocationException {
        return allocManager.deleteAllocation(allocationId);
    }

    public List<Allocation> getAllocationsByPolicyStatus(String status) throws SQLException {
        return allocManager.getAllocationsByStatus(status);
    }

    public List<Allocation> getAllocationsByPolicyId(String policyId) throws SQLException {
        return allocManager.getAllocationsByPolicyId(policyId);
    }
    public Allocation getAllocationById(String allocationId) throws SQLException {
        return allocManager.getAllocationById(allocationId);
    }
    public void updateStatusClaim(String allocationId, String status, double updatedSum) throws SQLException {
        allocManager.updateStatusClaim(allocationId, status, updatedSum);
    }

    // Call dataRetrieval from AllocationManagement
    public List<Allocation> dataRetrieval(String allocationId) throws SQLException {
        return allocManager.dataRetrieval(allocationId);
    }
    public void updateStatuspay(String allocationId, double totalPayment) throws SQLException {
        allocManager.updateTotalPayment(allocationId, totalPayment);
    }

}