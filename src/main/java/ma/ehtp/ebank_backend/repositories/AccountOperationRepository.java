package ma.ehtp.ebank_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ehtp.ebank_backend.entities.AccountOperation;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

}
