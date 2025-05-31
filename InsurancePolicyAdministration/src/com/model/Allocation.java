package com.model;

public class Allocation {
	private String allocationId;
	private String customerId;
	private String policyId;
	private String nomineeName;
	 private double sumAssured;
	 private int noOfYears;
	 private double premiumAmount;
	 private String premiumPaymentCycle;
	 private double totalPayment;
	 private String policyStatus;
	 
	 public String getPolicyStatus() {
		return policyStatus;
	}
	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	
	public String getAllocationId() {
		return allocationId;
	}
	public void setAllocationId(String allocationId) {
		this.allocationId = allocationId;
	}
	public String getNomineeName() {
		return nomineeName;
	}
	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}
	public double getSumAssured() {
		return sumAssured;
	}
	public void setSumAssured(double sumAssured) {
		this.sumAssured = sumAssured;
	}
	public int getNoOfYears() {
		return noOfYears;
	}
	public void setNoOfYears(int noOfYears) {
		this.noOfYears = noOfYears;
	}
	public double getPremiumAmount() {
		return premiumAmount;
	}
	public void setPremiumAmount(double premiumAmount) {
		this.premiumAmount = premiumAmount;
	}
	public String getPremiumPaymentCycle() {
		return premiumPaymentCycle;
	}
	public void setPremiumPaymentCycle(String premiumPaymentCycle) {
		this.premiumPaymentCycle = premiumPaymentCycle;
	}
	public double getTotalPayment() {
		return totalPayment;
	}
	public void setTotalPayment(double totalPayment) {
		this.totalPayment = totalPayment;
	}
	public Allocation() {
		super();
	}
	public Allocation(String allocationId, String customerId, String policyId, String nomineeName, double sumAssured,
			int noOfYears, double premiumAmount, String premiumPaymentCycle, double totalPayment, String policyStatus) {
		super();
		this.allocationId = allocationId;
		this.customerId = customerId;
		this.policyId = policyId;
		this.nomineeName = nomineeName;
		this.sumAssured = sumAssured;
		this.noOfYears = noOfYears;
		this.premiumAmount = premiumAmount;
		this.premiumPaymentCycle = premiumPaymentCycle;
		this.totalPayment = totalPayment;
		this.policyStatus = policyStatus;
	}
	
	public String toString() {
	    java.text.DecimalFormat formatter = new java.text.DecimalFormat("#,##0.00");
	    return "Allocation ID: " + allocationId +
	           "\nCustomer ID: " + customerId +
	           "\nPolicy ID: " + policyId +
	           "\nNominee: " + nomineeName +
	           "\nSum Assured: " + sumAssured +
	           "\nYears: " + noOfYears +
	           "\nPremium: " + formatter.format(premiumAmount) +
	           "\nPayment Cycle: " + premiumPaymentCycle +
	           "\nTotal Payment: " + formatter.format(totalPayment) +
	           "\nPolicy Status: " + policyStatus;
	}
 
}
