package ma.ehtp.ebank_backend.web;


import ma.ehtp.ebank_backend.DTOs.AccountHistoryDTO;
import ma.ehtp.ebank_backend.DTOs.AccountOperationDTO;
import ma.ehtp.ebank_backend.DTOs.BankAccountDTO;
import ma.ehtp.ebank_backend.exceptions.BankAccountNotFoundException;
import ma.ehtp.ebank_backend.services.BanckAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BankAccountRestAPI {


    private final BanckAccountService bankAccountService;

    public BankAccountRestAPI(BanckAccountService banckAccountService){
        this.bankAccountService = banckAccountService;
    }

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);

    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listBankAccount(){
        return bankAccountService.listBankAccount();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperation")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId
            , @RequestParam(name = "page", defaultValue = "0") int page
            , @RequestParam(name = "size", defaultValue = "5") int size) throws BankAccountNotFoundException{
        return bankAccountService.getAccountHistory(accountId,page,size);
    }
}
