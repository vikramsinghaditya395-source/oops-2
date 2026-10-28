package com.voltpay.repository;
import com.voltpay.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface BillRepository extends JpaRepository<Bill,Long> {
    List<Bill> findByCustomerIdOrderByPeriodDesc(Long customerId);
    Optional<Bill> findByCustomerIdAndPeriod(Long customerId, String period);
}
