package ma.ehtp.ebank_backend.mappers;

import ma.ehtp.ebank_backend.DTOs.AccountOperationDTO;
import ma.ehtp.ebank_backend.DTOs.CurrentBankAccountDTO;
import ma.ehtp.ebank_backend.DTOs.SavingBankAccountDTO;
import ma.ehtp.ebank_backend.entities.AccountOperation;
import ma.ehtp.ebank_backend.entities.CurrentAccount;
import ma.ehtp.ebank_backend.entities.Customer;
import ma.ehtp.ebank_backend.DTOs.CustomerDTO;
import ma.ehtp.ebank_backend.entities.SavingAccount;

public interface BankAccountMapper {

    CustomerDTO fromCustomer(Customer customer);

    Customer fromCustomerDTO(CustomerDTO customerDTO);

    SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount);

    SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO);

    CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount);
    CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO);

    AccountOperationDTO fromAccountOperation(AccountOperation accountOperation);
    AccountOperation fromAccountOperationDTO(AccountOperationDTO accountOperationDTO);

}
