package com.voltpay.controller;
import com.voltpay.model.Tariff;
import com.voltpay.service.TariffService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/tariffs")
public class TariffController {
    private final TariffService service;
    public TariffController(TariffService service){this.service=service;}
    @GetMapping public List<Tariff> all(){return service.findAll();}
    @PutMapping("/{category}") public Tariff update(@PathVariable String category,@RequestBody TariffRequest request){
        return service.save(category,request.rate(),request.fixedCharge());
    }
    public record TariffRequest(BigDecimal rate, BigDecimal fixedCharge){}
}
