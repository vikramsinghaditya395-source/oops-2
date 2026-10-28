package com.voltpay.service;
import com.voltpay.model.Customer;
import com.voltpay.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customers;
    public CustomerService(CustomerRepository customers){this.customers=customers;}
    public List<Customer> findAll(){return customers.findAll();}
    public Customer findByCode(String code){return customers.findByCustomerCode(code)
        .orElseThrow(()->new IllegalArgumentException("Customer not found: "+code));}
    public Customer create(Customer customer){return customers.save(customer);}
    public Customer findForDemoLogin(String code, String mobile){
        return customers.findByCustomerCodeAndMobile(code,mobile)
            .orElseThrow(()->new IllegalArgumentException("Customer ID and mobile do not match."));
    }
}
