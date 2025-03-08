package ma.ehtp.ebank_backend.services;

import java.util.List;

import ma.ehtp.ebank_backend.DTOs.*;
import ma.ehtp.ebank_backend.entities.BankAccount;
import ma.ehtp.ebank_backend.entities.CurrentAccount;
import ma.ehtp.ebank_backend.entities.SavingAccount;
import ma.ehtp.ebank_backend.exceptions.BalanceNotSufficientExeption;
import ma.ehtp.ebank_backend.exceptions.BankAccountNotFoundException;
import ma.ehtp.ebank_backend.exceptions.CustomerNotFoundException;


public interface BanckAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentAccount(double initalBalance, double overDraft, Long customerID) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingAccount(double initalBalance, double interestRate, Long customerID) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountID) throws BankAccountNotFoundException;
    void debit(String accountID, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientExeption;
    void credit(String accountID, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDestination, double amount)throws BankAccountNotFoundException, BalanceNotSufficientExeption;
    List<BankAccountDTO> listBankAccount();
    CustomerDTO getCustomer(Long id) throws CustomerNotFoundException;
    void deleteCustomer(Long customerId);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    List<AccountOperationDTO> accountHistory(String accountId);
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
    

}
