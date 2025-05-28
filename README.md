
# Insurance Policy Administration System – Star Life Insurance Company

This is a Java-based **Insurance Policy Administration System** designed for **Star Life Insurance Company**. It manages customers, insurance policies, allocations, payments, and claims efficiently through a structured and modular software design.

## 📌 Overview

The system streamlines administrative processes involved in policy management and offers a clear interface for managing customer interactions, policy issuance, payment processing, and claim settlements.

## ✅ Key Features

- **Customer Management** – Register, update, and view customer profiles.
- **Policy Management** – Manage insurance policy data and details.
- **Policy Allocation** – Assign policies to registered customers.
- **Payment Processing** – Record and manage policy payments.
- **Claim Handling** – Process insurance claims based on predefined criteria.
- **Robust Error Handling** – Custom exceptions for invalid operations.
- **Database Integration** – JDBC connectivity with a MySQL database.

## 🛠 Technologies Used

- Java SE (Standard Edition)
- MySQL Database
- JDBC (Java Database Connectivity)
- Eclipse IDE
- `.properties` file for database config

## 📂 Project Structure

com/
├── client/              # Entry point: UserInterface.java
├── exception/           # Custom exception classes
├── management/          # Business logic for system modules
├── model/               # POJO classes (Customer, Policy, etc.)
├── service/             # Core service implementations
```

## ⚙️ Setup Instructions

1. **Clone or Download the Project**
   git clone [<repo-url>](https://github.com/Shruthi018/Star-Life-Insurance-Company)
   cd InsurancePolicyAdministration

2. **Configure Database**
   - Create a MySQL database named `insurance_db` (or your choice).
   - Edit `database.properties` with correct database URL, username, and password.

3. **Import into Eclipse**
   - Open Eclipse IDE.
   - Go to `File > Import > Existing Projects into Workspace`.
   - Choose the project folder to import.

4. **Run the Project**
   - Locate and open `UserInterface.java` in `com.client` package.
   - Right-click and select `Run As > Java Application`.
