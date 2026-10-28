package com.voltpay.service;
import com.voltpay.model.Tariff;
import com.voltpay.repository.TariffRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class TariffService {
    private final TariffRepository tariffs;
    public TariffService(TariffRepository tariffs){this.tariffs=tariffs;}
    public List<Tariff> findAll(){return tariffs.findAll();}
    public Tariff find(String category){return tariffs.findByCategory(category)
        .orElseThrow(()->new IllegalArgumentException("Tariff not configured: "+category));}
    public Tariff save(String category, BigDecimal rate, BigDecimal fixed) {
        if(rate.signum()<0 || fixed.signum()<0) throw new IllegalArgumentException("Tariff values cannot be negative.");
        Tariff t=tariffs.findByCategory(category).orElse(new Tariff(category,rate,fixed));
        t.update(rate,fixed); return tariffs.save(t);
    }
}
