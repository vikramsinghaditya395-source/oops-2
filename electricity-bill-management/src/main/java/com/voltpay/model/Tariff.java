package com.voltpay.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="tariffs")
public class Tariff {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true) private String category;
    @Column(nullable=false) private BigDecimal rate;
    @Column(nullable=false) private BigDecimal fixedCharge;
    protected Tariff() {}
    public Tariff(String category, BigDecimal rate, BigDecimal fixedCharge) {
        this.category=category; this.rate=rate; this.fixedCharge=fixedCharge;
    }
    public Long getId(){return id;}
    public String getCategory(){return category;}
    public BigDecimal getRate(){return rate;}
    public BigDecimal getFixedCharge(){return fixedCharge;}
    public void update(BigDecimal rate, BigDecimal fixedCharge) {
        if(rate.signum()<0 || fixedCharge.signum()<0) throw new IllegalArgumentException("Tariff values cannot be negative.");
        this.rate=rate; this.fixedCharge=fixedCharge;
    }
}
