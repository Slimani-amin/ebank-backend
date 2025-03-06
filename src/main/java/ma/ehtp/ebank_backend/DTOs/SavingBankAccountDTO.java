package ma.ehtp.ebank_backend.DTOs;

import lombok.Data;
import ma.ehtp.ebank_backend.enums.AccountStatus;

import java.util.Date;


@Data
public class SavingBankAccountDTO extends BankAccountDTO {

    private double interestRate;
}
