package com.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.management.PaymentManagement;
import com.model.Allocation;
import com.model.Payment;

public class PaymentService {
    private List<Payment> payList = new ArrayList<>();
    AllocationService as = new AllocationService();
    PaymentManagement pm = new PaymentManagement();

    // Unique Payment ID generator
    public static int num2 = 100 + existId();

    public String generatePaymentId() {
        num2++;
        return "Pay" + num2;
    }

    // Create payment and return premium amount
    public double paymentCreation(String cusid, String allocid, int type) throws SQLException {
        List<Payment> plist = getData();
        int count = 0;

        for (Payment x : plist) {
            if (x.getAllocationId().equalsIgnoreCase(allocid)) {
                count += x.getInstallmentCount();
            }
        }

        String ptype = (type == 1) ? "UPI" : "CARD";
        double preamount = paymentCalc(allocid);
        Date d = new java.sql.Date(System.currentTimeMillis());

        count++;

        Payment pobj = new Payment(generatePaymentId(), cusid, allocid, preamount, d, ptype, count);
        payList.add(pobj);
        return preamount;
    }

    // Add payment to database
    public boolean addPayment() throws ClassNotFoundException {
        boolean status = pm.addPayment(payList);
        if (status) {
            payList.clear(); // Clear list after successful DB insertion
        }
        return status;
    }

    // Calculate installment and update total paid
    public double paymentCalc(String id) throws SQLException {
        List<Allocation> li = as.dataRetrieval(id);
        double preamount = 0;
        double tot = 0;

        for (Allocation x : li) {
            if (x.getAllocationId().equalsIgnoreCase(id)) {
                preamount = x.getPremiumAmount();
                tot = preamount + x.getTotalPayment();
            }
        }

        as.updateStatuspay(id, tot);
        return preamount;
    }

    // Check existing payment count
    public static int existId() {
        PaymentManagement as = new PaymentManagement();
        return as.checkexists();
    }

    // Retrieve all payment data
    public List<Payment> getData() throws SQLException {
        return pm.viewData();
    }

    // Delete payment by ID
    public boolean delete(String id) throws SQLException {
        return pm.delete(id);
    }
    public boolean upidValid(String id) {
        return id.matches("[a-z0-9]{10,}(@paytm|@oksbi|@okaxis)");
    }

    public boolean cardValid(String id) {
        return id.matches("\\d{4}[ -]?\\d{4}[ -]?\\d{4}[ -]?\\d{4}");
    }

}
