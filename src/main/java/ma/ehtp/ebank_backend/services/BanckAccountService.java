package ma.ehtp.ebank_backend.services;

import java.util.List;

import ma.ehtp.ebank_backend.entities.BankAccount;
import ma.ehtp.ebank_backend.entities.CurrentAccount;
import ma.ehtp.ebank_backend.entities.Customer;
import ma.ehtp.ebank_backend.entities.SavingAccount;
import ma.ehtp.ebank_backend.exceptions.BalanceNotSufficientExeption;
import ma.ehtp.ebank_backend.exceptions.BankAccountNotFoundException;
import ma.ehtp.ebank_backend.exceptions.CustomerNotFoundException;

public interface BanckAccountService {
    Customer saveCustomer(Customer customer);
    CurrentAccount saveCurrentAccount(double initalBalance, double overDraft, Long customerID) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initalBalance, double interestRate, Long customerID) throws CustomerNotFoundException;
    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountID) throws BankAccountNotFoundException;
    void debit(String accountID, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientExeption;
    void credit(String accountID, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDestination, double amount);
    

}
