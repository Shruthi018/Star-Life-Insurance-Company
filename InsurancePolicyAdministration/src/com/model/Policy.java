package com.model;

public class Policy {
	private String policyId;
	private int schemeNumber;
	private String policyName;
	private String policyType;
	private int maxNoOfYears;
	private double premiumRate;
	private int maxSumAssured;
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public int getSchemeNumber() {
		return schemeNumber;
	}
	public void setSchemeNumber(int schemeNumber) {
		this.schemeNumber = schemeNumber;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public int getMaxNoOfYears() {
		return maxNoOfYears;
	}
	public void setMaxNoOfYears(int maxNoOfYears) {
		this.maxNoOfYears = maxNoOfYears;
	}
	public double getPremiumRate() {
		return premiumRate;
	}
	public void setPremiumRate(double premiumRate) {
		this.premiumRate = premiumRate;
	}
	public int getMaxSumAssured() {
		return maxSumAssured;
	}
	public void setMaxSumAssured(int maxSumAssured) {
		this.maxSumAssured = maxSumAssured;
	}
	public Policy(String policyId, int schemeNumber, String policyName, String policyType, int maxNoOfYears,
			double premiumRate, int maxSumAssured) {
		super();
		this.policyId = policyId;
		this.schemeNumber = schemeNumber;
		this.policyName = policyName;
		this.policyType = policyType;
		this.maxNoOfYears = maxNoOfYears;
		this.premiumRate = premiumRate;
		this.maxSumAssured = maxSumAssured;
	}
	public String toString() {
	    return "Policy ID: " + policyId +
	           "\nScheme Number: " + schemeNumber +
	           "\nPolicy Name: " + policyName +
	           "\nPolicy Type: " + policyType +
	           "\nMax Years: " + maxNoOfYears +
	           "\nPremium Rate: " + premiumRate +
	           "\nMax Sum Assured: " + maxSumAssured;
	}

}