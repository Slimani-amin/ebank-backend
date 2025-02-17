package ma.ehtp.ebank_backend.entities;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ehtp.ebank_backend.enums.OperationType;


@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperation {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType operationType;
    private BankAccount bankAccount;

}
