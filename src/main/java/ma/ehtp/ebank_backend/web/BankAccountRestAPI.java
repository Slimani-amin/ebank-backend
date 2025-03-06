package ma.ehtp.ebank_backend.web;


import ma.ehtp.ebank_backend.DTOs.BankAccountDTO;
import ma.ehtp.ebank_backend.exceptions.BankAccountNotFoundException;
import ma.ehtp.ebank_backend.services.BanckAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BankAccountRestAPI {


    private BanckAccountService banckAccountService;

    public BankAccountRestAPI(BanckAccountService banckAccountService){
        this.banckAccountService = banckAccountService;
    }

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountID) throws BankAccountNotFoundException {
        return banckAccountService.getBankAccount(accountID);

    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listBankAccount(){
        return banckAccountService.listBankAccount();
    }

}
