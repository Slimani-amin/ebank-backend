package ma.ehtp.ebank_backend.DTOs;

import lombok.Data;
import ma.ehtp.ebank_backend.enums.AccountStatus;

import java.util.Date;

@Data
public class BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private String type;
}
