package com.voltpay.controller;
import com.voltpay.dto.BillRequest;
import com.voltpay.model.Bill;
import com.voltpay.service.BillService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    private final BillService service;
    public BillController(BillService service){this.service=service;}
    @GetMapping public List<Bill> all(){return service.all();}
    @GetMapping("/customer/{code}") public List<Bill> byCustomer(@PathVariable String code){return service.byCustomer(code);}
    @PostMapping public Bill createOrUpdate(@Valid @RequestBody BillRequest request){return service.save(request);}
    @PatchMapping("/{id}/pay") public Bill markPaid(@PathVariable Long id){return service.markPaid(id);}
}
