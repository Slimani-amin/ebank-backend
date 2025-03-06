package ma.ehtp.ebank_backend.DTOs;

import lombok.Data;
import ma.ehtp.ebank_backend.enums.AccountStatus;

import java.util.Date;


@Data
public class CurrentBankAccountDTO extends BankAccountDTO{
    private double overDraft;
}
