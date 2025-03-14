package ma.ehtp.ebank_backend.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import ma.ehtp.ebank_backend.DTOs.*;
import ma.ehtp.ebank_backend.mappers.BankAccountMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private BankAccountMapper dtoMapper;

    @Override 
    public CustomerDTO saveCustomer(CustomerDTO customerDTO){
        log.info("Saving un new Customer");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    

    @Override   
    public CurrentBankAccountDTO saveCurrentAccount(double initalBalance, double overDraft, Long customerID) throws CustomerNotFoundException{
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
            CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
            return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    
    
    
        }

     @Override 
      public SavingBankAccountDTO saveSavingAccount(double initalBalance, double interestRate, Long customerID) throws CustomerNotFoundException{
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

        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
        
        return dtoMapper.fromSavingBankAccount(savedBankAccount);

      }

      @Override
      public List<CustomerDTO> listCustomers(){
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> colec = customers.parallelStream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        return colec;
      }

      @Override
      public BankAccountDTO getBankAccount(String accountID) throws BankAccountNotFoundException{
        BankAccount bankAccount =  bankAccountRepository.findById(accountID).orElse(null);
        if(bankAccount == null){
            throw new BankAccountNotFoundException("Bank Account not found");
        }

        if(bankAccount instanceof  SavingAccount){
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return dtoMapper.fromSavingBankAccount(savingAccount);

        }else{
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentBankAccount(currentAccount);
        }

      }

      @Override
      public  void debit(String accountID, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientExeption{
          BankAccount bankAccount =  bankAccountRepository.findById(accountID).orElse(null);
          if(bankAccount == null){
              throw new BankAccountNotFoundException("Bank Account not found");
          }
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
          BankAccount bankAccount =  bankAccountRepository.findById(accountID).orElse(null);
          if(bankAccount == null){
              throw new BankAccountNotFoundException("Bank Account not found");
          }
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
       public List<BankAccountDTO> listBankAccount(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
           if(bankAccount instanceof SavingAccount){
               SavingAccount savingAccount = (SavingAccount) bankAccount;
               return dtoMapper.fromSavingBankAccount(savingAccount);
           }
           else{
               CurrentAccount currentAccount = (CurrentAccount) bankAccount;
               return dtoMapper.fromCurrentBankAccount(currentAccount);
           }
        }).collect(Collectors.toList());

        return bankAccountDTOS;
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

    @Override
    public List<AccountOperationDTO> accountHistory(String accountID) {
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountID);
        List<AccountOperationDTO> accountOperationDTOS =accountOperations.stream().map(accountOperation -> {
            return dtoMapper.fromAccountOperation(accountOperation);
        }).collect(Collectors.toList());
        return accountOperationDTOS;

    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException{
        Page<AccountOperation> accountOperations =  accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        accountHistoryDTO.setAccountId(accountId);
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op->{
            return dtoMapper.fromAccountOperation(op);
        }).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null){
            throw new BankAccountNotFoundException("Bank Account not found");
        }
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        accountHistoryDTO.setPageSize(size);

        return  accountHistoryDTO;


    }


}
