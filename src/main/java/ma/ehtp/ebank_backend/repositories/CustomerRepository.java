package ma.ehtp.ebank_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ehtp.ebank_backend.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
