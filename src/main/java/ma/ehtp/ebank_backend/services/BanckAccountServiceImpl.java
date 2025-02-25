package ma.ehtp.ebank_backend.services;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.ehtp.ebank_backend.entities.AccountOperation;
import ma.ehtp.ebank_backend.entities.BankAccount;
import ma.ehtp.ebank_backend.entities.CurrentAccount;
import ma.ehtp.ebank_backend.entities.Customer;
import ma.ehtp.ebank_backend.entities.SavingAccount;
import ma.ehtp.ebank_backend.enums.OperationType;
import ma.ehtp.ebank_backend.exceptions.BalanceNotSufficientExeption;
import ma.ehtp.ebank_backend.exceptions.BankAccountNotFoundException;
import ma.ehtp.ebank_backend.exceptions.CustomerNotFoundException;
import ma.ehtp.ebank_backend.repositories.AccountOperationRepository;
import ma.ehtp.ebank_backend.repositories.BankAccountRepository;
import ma.ehtp.ebank_backend.repositories.CustomerRepository;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BanckAccountServiceImpl implements BanckAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;

    @Override 
    public Customer saveCustomer(Customer customer){
        log.info("Saving un new Customer");
        return customerRepository.save(customer);
    }

    

    @Override   
    public CurrentAccount saveCurrentAccount(double initalBalance, double overDraft, Long customerID) throws CustomerNotFoundException{
            Customer customer = customerRepository.findById(customerID).orElse(null);
            if(customer == null){
                throw new CustomerNotFoundException("Customer not fond");
    
            }
            CurrentAccount currentAccount = new CurrentAccount();
            currentAccount.setBalance(initalBalance);
            currentAccount.setCustomer(customer);
            currentAccount.setCreatedAt(new Date());
            currentAccount.setOverDraft(overDraft);
            return bankAccountRepository.save(currentAccount);
    
    
    
        }

     @Override 
      public SavingAccount saveSavingBankAccount(double initalBalance, double interestRate, Long customerID) throws CustomerNotFoundException{
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if(customer == null){
            throw new CustomerNotFoundException("Customer not fond");
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setBalance(initalBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setInterestRate(interestRate);
        
        return bankAccountRepository.save(savingAccount);

      }

      @Override
      public List<Customer> listCustomers(){
        return customerRepository.findAll();
      }

      @Override
      public BankAccount getBankAccount(String accountID) throws BankAccountNotFoundException{
        BankAccount bankAccount =  bankAccountRepository.findById(accountID).orElse(null);
        if(bankAccount == null){
            throw new BankAccountNotFoundException("Bank Account not found");
        }
        return bankAccount;
      }

      @Override
      public  void debit(String accountID, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientExeption{
        BankAccount bankAccount = getBankAccount(accountID);
        if(bankAccount.getBalance()<amount){
            throw new BalanceNotSufficientExeption("Balance not Sufficient");
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setOperationType(OperationType.DEBIT);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        accountOperation.setBankAccount(bankAccount);
        bankAccount.getAccountOperations().add(accountOperation);
        bankAccountRepository.save(bankAccount);
        accountOperationRepository.save(accountOperation);



      }

      @Override
      public void credit(String accountID, double amount, String description) throws BankAccountNotFoundException{
        BankAccount bankAccount = getBankAccount(accountID);
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setOperationType(OperationType.CREDIT);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccount.getAccountOperations().add(accountOperation);
        accountOperation.setBankAccount(bankAccount);
        bankAccountRepository.save(bankAccount);
        accountOperationRepository.save(accountOperation);
      }
   
       @Override
       public void transfer(String accountIdSource,String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientExeption{
        debit(accountIdSource, amount, "Transfer to "+accountIdDestination);
        credit(accountIdDestination, amount, "Transfer from "+accountIdSource);
       }
    



    
    

    



}
