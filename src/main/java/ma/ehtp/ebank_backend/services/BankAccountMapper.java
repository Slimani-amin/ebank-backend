package ma.ehtp.ebank_backend.services;

import ma.ehtp.ebank_backend.entities.Customer;
import ma.ehtp.ebank_backend.mappers.CustomerDTO;

public interface BankAccountMapper {

    CustomerDTO fromCustomer(Customer customer);

    Customer fromCustomerDTO(CustomerDTO customerDTO);

}
