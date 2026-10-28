package com.voltpay.controller;
import com.voltpay.model.Customer;
import com.voltpay.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService service;
    public CustomerController(CustomerService service){this.service=service;}
    @GetMapping public List<Customer> all(){return service.findAll();}
    @GetMapping("/{code}") public Customer byCode(@PathVariable String code){return service.findByCode(code);}
    @PostMapping public Customer create(@Valid @RequestBody Customer customer){return service.create(customer);}
    // Demo-only lookup; replace with OTP delivery + secure session/JWT before deployment.
    @PostMapping("/demo-login") public Customer demoLogin(@RequestBody LoginRequest request){
        return service.findForDemoLogin(request.customerCode(),request.mobile());
    }
    public record LoginRequest(String customerCode,String mobile){}
}
