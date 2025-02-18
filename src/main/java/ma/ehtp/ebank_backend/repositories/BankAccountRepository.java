package ma.ehtp.ebank_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ehtp.ebank_backend.entities.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

}
