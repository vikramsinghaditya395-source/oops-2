package com.voltpay.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="bills", uniqueConstraints=@UniqueConstraint(columnNames={"customer_id","period"}))
public class Bill {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="customer_id")
    private Customer customer;
    @Column(nullable=false) private String period;
    private long previousReading;
    private long currentReading;
    private long units;
    private String category;
    private BigDecimal rate;
    private BigDecimal fixedCharge;
    private BigDecimal energyCharge;
    private BigDecimal amount;
    private LocalDate dueDate;
    private String status = "UNPAID";
    private LocalDateTime createdAt = LocalDateTime.now();

    protected Bill() {}
    public Bill(Customer customer, String period, long previousReading, long currentReading, String category,
                BigDecimal rate, BigDecimal fixedCharge, LocalDate dueDate) {
        this.customer=customer; this.period=period; this.previousReading=previousReading;
        this.currentReading=currentReading; this.units=currentReading-previousReading; this.category=category;
        this.rate=rate; this.fixedCharge=fixedCharge; this.energyCharge=rate.multiply(BigDecimal.valueOf(units));
        this.amount=energyCharge.add(fixedCharge); this.dueDate=dueDate; this.status="UNPAID";
        this.createdAt=LocalDateTime.now();
    }
    public void updateReadings(long previous, long current, String category, BigDecimal rate, BigDecimal fixed, LocalDate due) {
        if(current < previous || previous < 0) throw new IllegalArgumentException("Current reading must be >= previous reading.");
        this.previousReading=previous; this.currentReading=current; this.units=current-previous;
        this.category=category; this.rate=rate; this.fixedCharge=fixed;
        this.energyCharge=rate.multiply(BigDecimal.valueOf(units)); this.amount=energyCharge.add(fixed); this.dueDate=due;
    }
    public void markPaid(){ if(!"PAID".equals(status)) status="PAID"; }
    public Long getId(){return id;}
    public Customer getCustomer(){return customer;}
    public String getPeriod(){return period;}
    public long getPreviousReading(){return previousReading;}
    public long getCurrentReading(){return currentReading;}
    public long getUnits(){return units;}
    public String getCategory(){return category;}
    public BigDecimal getRate(){return rate;}
    public BigDecimal getFixedCharge(){return fixedCharge;}
    public BigDecimal getEnergyCharge(){return energyCharge;}
    public BigDecimal getAmount(){return amount;}
    public LocalDate getDueDate(){return dueDate;}
    public String getStatus(){return status;}
    public LocalDateTime getCreatedAt(){return createdAt;}
}
