package ma.ehtp.ebank_backend.web;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.ehtp.ebank_backend.exceptions.CustomerNotFoundException;
import ma.ehtp.ebank_backend.mappers.CustomerDTO;
import ma.ehtp.ebank_backend.services.BanckAccountService;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
      private BanckAccountService banckAccountService;
    
       @GetMapping("/customers") 
      public List<CustomerDTO> customers(){
        return banckAccountService.listCustomers();
      }

      @GetMapping("/customers/{id}")
      public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {

          return banckAccountService.getCustomer(customerId);
      }

      @PostMapping("/customers")
      public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return banckAccountService.saveCustomer(customerDTO);
      }

      @PutMapping("/customers/{customerId}")
      public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO) {
          customerDTO.setId(customerId);
          return banckAccountService.updateCustomer(customerDTO);
      }
 
      @DeleteMapping("/customers/{id}")
      public void deleteCustomer(@PathVariable Long id){
        banckAccountService.deleteCustomer(id);
      }

}

