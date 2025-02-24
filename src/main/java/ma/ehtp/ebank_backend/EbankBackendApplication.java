package ma.ehtp.ebank_backend;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ma.ehtp.ebank_backend.entities.AccountOperation;
import ma.ehtp.ebank_backend.entities.BankAccount;
import ma.ehtp.ebank_backend.entities.CurrentAccount;
import ma.ehtp.ebank_backend.entities.Customer;
import ma.ehtp.ebank_backend.entities.SavingAccount;
import ma.ehtp.ebank_backend.enums.AccountStatus;
import ma.ehtp.ebank_backend.enums.OperationType;
import ma.ehtp.ebank_backend.repositories.AccountOperationRepository;
import ma.ehtp.ebank_backend.repositories.BankAccountRepository;
import ma.ehtp.ebank_backend.repositories.CustomerRepository;
import ma.ehtp.ebank_backend.services.BankService;

@SpringBootApplication
public class EbankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankBackendApplication.class, args);
	}

    @Bean
	CommandLineRunner commandLineRunner(BankService bankService) {
		return args -> {
			bankService.consulter();
			



		};
	}

    //@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
			AccountOperationRepository accountOperationRepository,
			BankAccountRepository bankAccountRepository) {
		return args -> {
			Stream.of("amine", "zaid", "farouk").forEach(c -> {
				Customer customer = new Customer();
				customer.setName(c);
				customer.setEmail(c + "@gmail.com");
				customerRepository.save(customer);
			});

			customerRepository.findAll().forEach(cust->{
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random()*90000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setCustomer(cust);
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setOverDraft(10000);
				bankAccountRepository.save(currentAccount);

				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random()*90000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setCustomer(cust);
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setInterestRate(5);
				bankAccountRepository.save(savingAccount);

				

			}

			);

			bankAccountRepository.findAll().forEach(acc->{
				for(int i=0;i<10;i++){
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setAmount(Math.random()*10000);
					accountOperation.setOperationDate(new Date());
					accountOperation.setOperationType(Math.random()>0.5?OperationType.DEBIT:OperationType.CREDIT);
					accountOperation.setBankAccount(acc);
					accountOperationRepository.save(accountOperation);
				}
				

			});
		};
	}

}



