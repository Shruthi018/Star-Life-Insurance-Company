package com.client;

import com.model.*;
import com.service.*;
import com.util.ApplicationUtil;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private static Scanner sc = new Scanner(System.in);
    private static CustomerService customerService = new CustomerService();
    private static PolicyService policyService = new PolicyService();
    private static AllocationService allocationService = new AllocationService();
    private static ClaimService claimService = new ClaimService();
    private static PaymentService paymentService = new PaymentService();

    public static void main(String[] args) {
        int mainChoice;

        while (true) {
            System.out.println("\n--- STAR LIFE INSURANCE COMPANY ---");
            System.out.println("1. Customer Services");
            System.out.println("2. Policy Services");
            System.out.println("3. Allocation Services");
            System.out.println("4. Payment Services");
            System.out.println("5. Claims Services");
            System.out.println("6. Exit Application");
            System.out.print("Please select an option: ");
            mainChoice = sc.nextInt();
            sc.nextLine();
            switch (mainChoice) {
                case 1:
                    customerMenu(sc);
                    break;
                case 2:
                    PolicyMenu(sc);
                    break;
                case 3:
                	allocationMenu(sc);
                	break;
                case 4:
                	paymentMenu(sc);
                    break;
                case 5:
                	claimsMenu(sc);
                    break;
                case 6:
                    System.out.println("Thank you for using STAR LIFE INSURANCE COMPANY. Goodbye!");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private static void customerMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--- CUSTOMER SERVICES ---");
            System.out.println("1. Add Customer (from string)");
            System.out.println("2. Update Full Customer Details");
            System.out.println("3. Update Customer Phone Number");
            System.out.println("4. Update Customer Email");
            System.out.println("5. Delete Customer");
            System.out.println("6. Get Customer by ID");
            System.out.println("7. View All Customers");
            System.out.println("8. Return to Main Menu");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addCustomerFromString();
                    break;
                case 2:
                    updateFullCustomer();
                    break;
                case 3:
                    updatePhoneNumber();
                    break;
                case 4:
                    updateEmail();
                    break;
                case 5:
                    deleteCustomer();
                    break;
                case 6:
                    getCustomerById();
                    break;
                case 7:
                    getAllCustomers();
                    break;
                case 8:
                    System.out.println("Returning to main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }

    private static void addCustomerFromString() {
        System.out.println("Enter customer details (format: Name:DOB(dd-MM-yyyy):Age:Gender:Occupation:Income:Medical History:Address:Phone:Email):");
        String input = sc.nextLine();
        Customer customer = ApplicationUtil.parseCustomerFromString(input);

        if (customer != null) {
            boolean result = customerService.addCustomer(customer);
            System.out.println(result ? "Customer added successfully." : "Failed to add customer.");
        } else {
            System.out.println("Invalid input format.");
        }
    }

    private static void updateFullCustomer() {
        System.out.println("Enter updated customer details (same format as Add):");
        String input = sc.nextLine();
        Customer customer = ApplicationUtil.parseCustomerFromString(input);

        if (customer != null) {
            System.out.print("Enter existing Customer ID to update: ");
            String id = sc.nextLine();
            customer.setCustomerId(id);

            boolean result = customerService.updateCustomer(customer);
            System.out.println(result ? "Customer updated successfully." : "Failed to update customer.");
        } else {
            System.out.println("Invalid input.");
        }
    }

    private static void updatePhoneNumber() {
        System.out.print("Enter Customer ID: ");
        String customerId = sc.nextLine();
        System.out.print("Enter new Phone Number: ");
        long phone = Long.parseLong(sc.nextLine());

        boolean result = customerService.updatePhoneNumber(customerId, phone);
        System.out.println(result ? "Phone number updated." : "Update failed.");
    }

    private static void updateEmail() {
        System.out.print("Enter Customer ID: ");
        String customerId = sc.nextLine();
        System.out.print("Enter new Email ID: ");
        String email = sc.nextLine();

        boolean result = customerService.updateEmailId(customerId, email);
        System.out.println(result ? "Email ID updated." : "Update failed.");
    }

    private static void deleteCustomer() {
        System.out.print("Enter Customer ID to delete: ");
        String id = sc.nextLine();

        boolean result = customerService.deleteCustomer(id);
        System.out.println(result ? "Customer deleted successfully." : "Delete failed.");
    }

    private static void getCustomerById() {
        System.out.print("Enter Customer ID: ");
        String id = sc.nextLine();

        Customer customer = customerService.getCustomer(id);
        if (customer != null) {
            System.out.println("Customer Details:\n" + customer);
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("All Customers:");
            for (Customer c : customers) {
                System.out.println(c);
            }
        }
    }

    private static void PolicyMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--- POLICY SERVICES ---");
            System.out.println("1. Add Policies from Input");
            System.out.println("2. View All Policies");
            System.out.println("3. Modify Max Sum Assured");
            System.out.println("4. Modify Max Years");
            System.out.println("5. Delete Policy");
            System.out.println("6. Return to Main Menu");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    addPoliciesFromInput();
                    break;
                case 2:
                	displayAllPolicies();
                    break;
                case 3:
                	modifySumAssured();
                    break;
                case 4:
                	modifyMaxYears();
                    break;
                case 5:
                	deletePolicy();
                    break;
                case 6:
                	System.out.println("Returning to main menu."); 
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 6);
    }

    private static void addPoliciesFromInput() {
        System.out.println("Enter policy details in the format:");
        System.out.println("schemeNo:name:type:maxYears:rate:maxSum");
        System.out.print("How many policies to add? ");
        int n = Integer.parseInt(sc.nextLine());
        String[] inputs = new String[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter policy " + (i + 1) + ": ");
            inputs[i] = sc.nextLine();
        }

        policyService.addList(inputs);
        int count = policyService.addPoliciesToDB();
        System.out.println(count + " policies inserted into the database.");
    }

    private static void displayAllPolicies() {
        List<Policy> policies = policyService.retrieveAllPolicies();
        if (policies.isEmpty()) {
            System.out.println("No policies found.");
        } else {
            for (Policy p : policies) {
                System.out.println(p);
            }
        }
    }

    private static void modifySumAssured() {
        System.out.print("Enter Policy ID: ");
        String id = sc.nextLine();
        System.out.print("Enter new Sum Assured: ");
        int amount = Integer.parseInt(sc.nextLine());
        boolean updated = policyService.modifySumAssured(id, amount);
        System.out.println(updated ? "Updated successfully." : "Update failed.");
    }

    private static void modifyMaxYears() {
        System.out.print("Enter Policy ID: ");
        String id = sc.nextLine();
        System.out.print("Enter new Max Years: ");
        int years = Integer.parseInt(sc.nextLine());
        boolean updated = policyService.modifyMaxYears(id, years);
        System.out.println(updated ? "Updated successfully." : "Update failed.");
    }

    private static void deletePolicy() {
        System.out.print("Enter Policy ID to delete: ");
        String id = sc.nextLine();
        boolean deleted = policyService.deletePolicy(id);
        System.out.println(deleted ? "Deleted successfully." : "Delete failed.");
    }
    
    private static void allocationMenu(Scanner sc) {
        int choice=0;
        do {
            System.out.println("\n--- ALLOCATION SERVICES ---");
            System.out.println("1. Create New Allocation");
            System.out.println("2. View Allocations");
            System.out.println("3. Delete Allocation by ID");
            System.out.println("4. Return to Main Menu");
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    createNewAllocation(sc);
                    break;
                case 2:
                	viewAllocations(sc);
                    break;
                case 3:
                    deleteAllocationById(sc);
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private static void createNewAllocation(Scanner sc) {
        try {
            System.out.print("Enter Customer ID: ");
            String customerId = sc.nextLine();

            System.out.print("Enter Policy ID: ");
            String policyId = sc.nextLine();

            System.out.print("Enter Nominee Name: ");
            String nomineeName = sc.nextLine();

            System.out.println("Select Premium Payment Cycle:");
            System.out.println("1. Half-year");
            System.out.println("2. Yearly");
            System.out.print("Enter choice (1 or 2): ");
            int cycle = Integer.parseInt(sc.nextLine());

            allocationService.calculatePremium(customerId, policyId, nomineeName, cycle);

            boolean saved = allocationService.saveAllocation();
            System.out.println(saved ? "Allocation created and saved successfully!" : "Failed to save allocation.");

        } catch (Exception e) {
            System.out.println("Error during allocation creation: " + e.getMessage());
        }
    }

    private static void viewAllocations(Scanner sc) {
        try {
            System.out.println("\nView Allocations By:");
            System.out.println("1. Policy Status");
            System.out.println("2. Policy ID");
            System.out.println("3. Allocation ID");
            System.out.print("Enter your choice: ");
            int viewChoice = Integer.parseInt(sc.nextLine());

            switch (viewChoice) {
                case 1:
                    System.out.print("Enter Policy Status (e.g., ACTIVE, INACTIVE): ");
                    String status = sc.nextLine().trim();
                    List<Allocation> byStatus = allocationService.getAllocationsByPolicyStatus(status);
                    printAllocations(byStatus);
                    break;

                case 2:
                    System.out.print("Enter Policy ID: ");
                    String policyId = sc.nextLine().trim();
                    List<Allocation> byPolicyId = allocationService.getAllocationsByPolicyId(policyId);
                    printAllocations(byPolicyId);
                    break;

                case 3:
                    System.out.print("Enter Allocation ID: ");
                    String allocId = sc.nextLine().trim();
                    Allocation alloc = allocationService.getAllocationById(allocId);
                    if (alloc != null) {
                        System.out.println(alloc);
                    } else {
                        System.out.println("No allocation found for ID: " + allocId);
                    }
                    break;

                default:
                    System.out.println("Invalid selection.");
            }
        } catch (Exception e) {
            System.out.println("Error fetching allocation data: " + e.getMessage());
        }
    }
    private static void printAllocations(List<Allocation> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("No allocations found.");
        } else {
            for (Allocation a : list) {
                System.out.println(a);
            }
        }
    }

    private static void deleteAllocationById(Scanner sc) {
        try {
            System.out.print("Enter Allocation ID to delete: ");
            String id = sc.nextLine().trim();

            boolean deleted = allocationService.deleteAllocation(id);
            System.out.println(deleted ? "Allocation deleted successfully." : "Deletion failed or ID not found.");

        } catch (Exception e) {
            System.out.println("Error deleting allocation: " + e.getMessage());
        }
    }
    private static void claimsMenu(Scanner sc) {
        int choice=0;
        do {
            System.out.println("\n--- CLAIM SERVICES ---");
            System.out.println("1. Create New Claim");
            System.out.println("2. View All Claims");
            System.out.println("3. Delete Claim by ID");
            System.out.println("4. Return to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    createNewClaim(sc);
                    break;
                case 2:
                	viewAllClaims();
                    break;
                case 3:
                	deleteClaimById(sc);
                    break;
                case 4:
                	System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }
    private static void createNewClaim(Scanner sc) {
        try {
            System.out.print("Enter Customer ID: ");
            String customerId = sc.nextLine();

            System.out.print("Enter Allocation ID: ");
            String allocationId = sc.nextLine();

            System.out.print("Enter Claim Type (death/maturity/survival benefit): ");
            String type = sc.nextLine();

            System.out.print("Enter Claim Date (dd-MM-yyyy): ");
            String date = sc.nextLine();

            claimService.createClaim(customerId, allocationId, type, date);
            double amount = claimService.addClaim();
            if (amount > 0) {
                System.out.println("Claim submitted successfully. Amount: ₹" + amount);
            } else {
                System.out.println("Claim submission failed.");
            }
        } catch (Exception e) {
            System.out.println("Error creating claim: " + e.getMessage());
        }
    }

    

    private static void viewAllClaims() {
        try {
            List<Claim> claims = claimService.getData();
            if (claims.isEmpty()) {
                System.out.println("No claims found.");
            } else {
                System.out.println("All Claims:");
                for (Claim c : claims) {
                    System.out.println(c);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving claims: " + e.getMessage());
        }
    }

    private static void deleteClaimById(Scanner sc) {
        System.out.print("Enter Claim ID to delete: ");
        String id = sc.nextLine();
        try {
            boolean deleted = claimService.delete(id);
            System.out.println(deleted ? "Claim deleted successfully." : "Claim not found or deletion failed.");
        } catch (Exception e) {
            System.out.println("Error deleting claim: " + e.getMessage());
        }
    }
    private static void paymentMenu(Scanner sc) {
        int choice = 0;
        do {
            System.out.println("\n--- PAYMENT SERVICES ---");
            System.out.println("1. Make a Payment");
            System.out.println("2. View All Payments");
            System.out.println("3. Delete Payment by ID");
            System.out.println("4. Return to Main Menu");
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    makePayment(sc);
                    break;
                case 2:
                    viewAllPayments();
                    break;
                case 3:
                    deletePaymentById(sc);
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private static void makePayment(Scanner sc) {
        try {
            System.out.print("Enter Customer ID: ");
            String customerId = sc.nextLine();

            System.out.print("Enter Allocation ID: ");
            String allocationId = sc.nextLine();

            System.out.println("Choose Payment Method:");
            System.out.println("1. UPI");
            System.out.println("2. CARD");
            System.out.print("Enter your choice: ");
            int method = Integer.parseInt(sc.nextLine());

            boolean valid = false;
            if (method == 1) {
                System.out.print("Enter UPI ID (e.g., abc12345@paytm): ");
                String upi = sc.nextLine();
                valid = paymentService.upidValid(upi);
                if (!valid) {
                    System.out.println("Invalid UPI format.");
                    return;
                }
            } else if (method == 2) {
                System.out.print("Enter Card Number (16 digits): ");
                String card = sc.nextLine();
                valid = paymentService.cardValid(card);
                if (!valid) {
                    System.out.println("Invalid card number format.");
                    return;
                }
            } else {
                System.out.println("Invalid payment method.");
                return;
            }

            double amount = paymentService.paymentCreation(customerId, allocationId, method);
            boolean success = paymentService.addPayment();

            if (success) {
    		    java.text.DecimalFormat formatter = new java.text.DecimalFormat("#,##0.00");

                System.out.println("Payment successful. Premium Paid: ₹" + formatter.format(amount));
            } else {
                System.out.println("Payment failed during database update.");
            }

        } catch (Exception e) {
            System.out.println("Error making payment: " + e.getMessage());
        }
    }

    private static void viewAllPayments() {
        try {
            List<Payment> payments = paymentService.getData();
            if (payments.isEmpty()) {
                System.out.println("No payments found.");
            } else {
                for (Payment p : payments) {
                    System.out.println(p);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving payments: " + e.getMessage());
        }
    }

    private static void deletePaymentById(Scanner sc) {
        try {
            System.out.print("Enter Payment ID to delete: ");
            String id = sc.nextLine();
            boolean result = paymentService.delete(id);
            System.out.println(result ? "Payment deleted successfully." : "Deletion failed or ID not found.");
        } catch (Exception e) {
            System.out.println("Error deleting payment: " + e.getMessage());
        }
    }


}
