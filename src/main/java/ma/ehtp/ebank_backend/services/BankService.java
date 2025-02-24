package ma.ehtp.ebank_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.ehtp.ebank_backend.entities.BankAccount;
import ma.ehtp.ebank_backend.entities.CurrentAccount;
import ma.ehtp.ebank_backend.entities.SavingAccount;
import ma.ehtp.ebank_backend.repositories.BankAccountRepository;


@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void consulter(){
        

        BankAccount bankAccount = bankAccountRepository.findById("9259a193-5917-4d1b-8f83-35bc36c9c5c3").orElse(null);
				System.out.println("************************************");
				System.out.println(bankAccount.getId());
				System.out.println(bankAccount.getBalance());
				System.out.println(bankAccount.getStatus());
				System.out.println(bankAccount.getCreatedAt());
				System.out.println(bankAccount.getCustomer().getName());
				System.out.println(bankAccount.getClass().getSimpleName());
				if(bankAccount instanceof CurrentAccount){
					System.out.println("Over Draft=>"+((CurrentAccount) bankAccount).getOverDraft());
				}
				else if(bankAccount instanceof SavingAccount){
					System.out.println("Rate=>"+((SavingAccount) bankAccount).getInterestRate());
				}

				bankAccount.getAccountOperations().forEach(op->{
					System.out.println("====================================");
					System.out.println(op.getId()+ "\t"+op.getAmount()+"\t"+op.getOperationDate()+"\t"+op.getOperationType());
					
				});

    }

}
