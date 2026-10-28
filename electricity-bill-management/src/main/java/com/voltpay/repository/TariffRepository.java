package com.voltpay.repository;
import com.voltpay.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface TariffRepository extends JpaRepository<Tariff,Long> {
    Optional<Tariff> findByCategory(String category);
}
