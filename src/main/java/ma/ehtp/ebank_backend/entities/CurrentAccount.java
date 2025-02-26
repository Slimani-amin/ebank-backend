package ma.ehtp.ebank_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CURR")
@Data
 @NoArgsConstructor 
 @AllArgsConstructor
public class CurrentAccount extends BankAccount {
    private double overDraft;

    

}
