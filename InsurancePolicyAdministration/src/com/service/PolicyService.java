package com.service;

import com.model.Policy;
import com.util.ApplicationUtil;
import com.management.PolicyManagement;

//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PolicyService {

    private final PolicyManagement policyManager = new PolicyManagement();
    //private final ApplicationUtil appUtil = new ApplicationUtil();
    private List<Policy> policyList = new ArrayList<>();

    // Getter for the internal policy list
    public List<Policy> getPolicyList() {
        return policyList;
    }

    // Setter for the internal policy list
    public void setPolicyList(List<Policy> policyList) {
        this.policyList = policyList;
    }

    // Create Policy objects from raw string inputs and populate the list
    public void addList(String... details) {
        List<Policy> list = new ArrayList<>();
        for (String record : details) {
            Policy policy = ApplicationUtil.parsePolicyFromString(record);
            if (policy != null) {
                list.add(policy);
            }
        }
        setPolicyList(list);
    }

    // Add all policies in the list to the database and return number of records added
    public int addPoliciesToDB() {
        return policyManager.addPolicies(policyList);
    }

    // Check if a given policy ID exists in the in-memory list
    public boolean isPolicyIdValidInList(String id) {
        for (Policy p : policyList) {
            if (p.getPolicyId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    // Check if a given policy ID exists in the database
    public boolean isPolicyIdValidInDB(String id) {
        return policyManager.policyExists(id);
    }

    // Modify sum assured of a policy in the database
    public boolean modifySumAssured(String policyId, int newSumAssured) {
        return policyManager.updatePolicy("MAX_SUM_ASSURED", newSumAssured, policyId);
    }

    // Modify max years of a policy in the database
    public boolean modifyMaxYears(String policyId, int newMaxYears) {
        return policyManager.updatePolicy("MAX_NO_OF_YEARS", newMaxYears, policyId);
    }

    // Delete a policy by ID
    public boolean deletePolicy(String policyId) {
        return policyManager.deletePolicy(policyId);
    }

    // Retrieve all policies from the database
    public List<Policy> retrieveAllPolicies() {
        return policyManager.getAllPolicies();
    }
}