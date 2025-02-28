package ma.ehtp.ebank_backend.services;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import ma.ehtp.ebank_backend.entities.Customer;
import ma.ehtp.ebank_backend.mappers.CustomerDTO;


//MapStruct?
@Service
public class BankAccountMapperImpl implements BankAccountMapper{

    @Override
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    @Override
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

}
