package ma.ehtp.ebank_backend.mappers;

import ma.ehtp.ebank_backend.DTOs.*;
import ma.ehtp.ebank_backend.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


//MapStruct?
@Service
public class BankAccountMapperImpl implements BankAccountMapper {

    @Override
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    @Override
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    @Override
    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount){
        SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingBankAccountDTO;
    }
    @Override
    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO){
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO, savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));

        return savingAccount;
    }

    @Override
    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount) {
        CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDTO;
    }

    @Override
    public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO, currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
    }

    @Override
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        BankAccount bankAccount = accountOperation.getBankAccount();
        if(bankAccount instanceof SavingAccount){
            accountOperationDTO.setBankAccountDTO(fromSavingBankAccount((SavingAccount) bankAccount));
        }
        else {
            accountOperationDTO.setBankAccountDTO(fromCurrentBankAccount((CurrentAccount) bankAccount));
        }
        return accountOperationDTO;
    }

    @Override
    public AccountOperation fromAccountOperationDTO(AccountOperationDTO accountOperationDTO) {
        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDTO, accountOperation);
        BankAccountDTO bankAccountDTO = accountOperationDTO.getBankAccountDTO();
        if(bankAccountDTO instanceof SavingBankAccountDTO){
            accountOperation.setBankAccount(fromSavingBankAccountDTO((SavingBankAccountDTO) bankAccountDTO));
        }
        else {
            accountOperation.setBankAccount(fromCurrentBankAccountDTO((CurrentBankAccountDTO) bankAccountDTO));
        }
        return accountOperation;
    }



}
