package ma.ehtp.ebank_backend.entities;

import java.util.Date;
import java.util.List;

import ma.ehtp.ebank_backend.enums.AccountStatus;

public class BankAccount {

    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private Customer customer;
    private List<AccountOperation> accountsOperations;

}
