package com.voltpay.service;
import com.voltpay.dto.BillRequest;
import com.voltpay.model.Bill;
import com.voltpay.model.Customer;
import com.voltpay.model.Tariff;
import com.voltpay.repository.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BillService {
    private final BillRepository bills;
    private final CustomerService customers;
    private final TariffService tariffs;
    public BillService(BillRepository bills, CustomerService customers, TariffService tariffs){
        this.bills=bills; this.customers=customers; this.tariffs=tariffs;
    }
    public List<Bill> byCustomer(String code){Customer c=customers.findByCode(code);return bills.findByCustomerIdOrderByPeriodDesc(c.getId());}
    public Bill save(BillRequest request){
        if(request.currentReading()<request.previousReading()) throw new IllegalArgumentException("Current reading must be >= previous reading.");
        Customer customer=customers.findByCode(request.customerCode());
        Tariff tariff=tariffs.find(request.category());
        Bill bill=bills.findByCustomerIdAndPeriod(customer.getId(),request.period())
            .orElseGet(()->new Bill(customer,request.period(),request.previousReading(),request.currentReading(),
                request.category(),tariff.getRate(),tariff.getFixedCharge(),request.dueDate()));
        bill.updateReadings(request.previousReading(),request.currentReading(),request.category(),tariff.getRate(),tariff.getFixedCharge(),request.dueDate());
        return bills.save(bill);
    }
    @Transactional
    public Bill markPaid(Long id){
        Bill bill=bills.findById(id).orElseThrow(()->new IllegalArgumentException("Bill not found: "+id));
        bill.markPaid(); return bill;
    }
    public List<Bill> all(){return bills.findAll();}
}
