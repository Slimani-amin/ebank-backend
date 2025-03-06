package ma.ehtp.ebank_backend.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
import ma.ehtp.ebank_backend.mappers.CustomerDTO;
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
    private BankAccountMapper dtoMapper;

    @Override 
    public CustomerDTO saveCustomer(CustomerDTO customerDTO){
        log.info("Saving un new Customer");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
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
            currentAccount.setId(UUID.randomUUID().toString());
            currentAccount.setCreatedAt(new Date());
            currentAccount.setOverDraft(overDraft);
            return bankAccountRepository.save(currentAccount);
    
    
    
        }

     @Override 
      public SavingAccount saveSavingAccount(double initalBalance, double interestRate, Long customerID) throws CustomerNotFoundException{
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if(customer == null){
            throw new CustomerNotFoundException("Customer not fond");
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setBalance(initalBalance);
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCustomer(customer);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setInterestRate(interestRate);
        
        return bankAccountRepository.save(savingAccount);

      }

      @Override
      public List<CustomerDTO> listCustomers(){
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> colec = customers.parallelStream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        return colec;
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

       @Override 
       public List<BankAccount> listBankAccount(){
        return bankAccountRepository.findAll();
       }

       @Override
       public CustomerDTO getCustomer(Long id) throws CustomerNotFoundException{
        Customer customer = new Customer();
        customer = customerRepository.getReferenceById(id);
        if(customer==null){
          throw new CustomerNotFoundException("Customer not found");
        }
        return dtoMapper.fromCustomer(customer);

       }

       @Override
       public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
       }

       @Override 
       public CustomerDTO updateCustomer(CustomerDTO customerDTO){
        log.info("Update Customer");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }


    



    
    

    



}
