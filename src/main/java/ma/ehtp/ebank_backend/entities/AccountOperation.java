package ma.ehtp.ebank_backend.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ehtp.ebank_backend.enums.OperationType;

/**
 * Entity representing an account operation.
 */
@Entity
@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class AccountOperation {
    /**
     * Unique identifier for the operation.
     */
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Date of the operation.
     */
    private Date operationDate;
    
    /**
     * Amount of the operation.
     */
    private double amount;
    
    /**
     * Type of the operation (e.g. deposit, withdrawal).
     */
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    
    /**
     * Associated bank account.
     */
    @ManyToOne
    private BankAccount bankAccount;
}
