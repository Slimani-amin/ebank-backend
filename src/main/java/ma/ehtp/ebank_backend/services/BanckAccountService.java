package ma.ehtp.ebank_backend.services;

import java.util.List;

import ma.ehtp.ebank_backend.entities.BankAccount;
import ma.ehtp.ebank_backend.entities.Customer;

public interface BanckAccountService {
    Customer saveCustomer(Customer customer);
    BankAccount saveBankAccount(double initalBalance, String accountType, Long customerID);
    List<Customer> listCustomers();
    BankAccount geBankAccount(String accountID);
    void debit(String accountID, double amount);
    void credit(String accountID, double amount);
    
    

}
