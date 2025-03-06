package ma.ehtp.ebank_backend.web;


import ma.ehtp.ebank_backend.services.BanckAccountService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountRestAPI {

    private BanckAccountService banckAccountService;

    public BankAccountRestAPI(BanckAccountService banckAccountService){
        this.banckAccountService = banckAccountService;
    }
    

}
