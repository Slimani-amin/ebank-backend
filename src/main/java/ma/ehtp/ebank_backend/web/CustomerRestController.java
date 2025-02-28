package ma.ehtp.ebank_backend.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.ehtp.ebank_backend.mappers.CustomerDTO;
import ma.ehtp.ebank_backend.services.BanckAccountService;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
      private BanckAccountService banckAccountService;
    
       @GetMapping("/customers")
       
      public List<CustomerDTO> customers(){
        return banckAccountService.listCustomers();
      }

}

