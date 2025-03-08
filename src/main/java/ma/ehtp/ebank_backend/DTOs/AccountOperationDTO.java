package ma.ehtp.ebank_backend.DTOs;

import jakarta.persistence.*;
import lombok.Data;
import ma.ehtp.ebank_backend.entities.BankAccount;
import ma.ehtp.ebank_backend.enums.OperationType;

import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType operationType;
    private BankAccountDTO bankAccountDTO;
    private String description;
}
