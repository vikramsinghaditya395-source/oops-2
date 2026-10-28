package com.voltpay.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true) @NotBlank
    private String customerCode;
    @NotBlank private String name;
    @Column(nullable=false, unique=true) @NotBlank
    private String mobile;
    @NotBlank private String connectionType;
    private String meterNumber;
    private String address;

    @OneToMany(mappedBy="customer", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Bill> bills = new ArrayList<>();

    protected Customer() {}
    public Customer(String customerCode, String name, String mobile, String connectionType, String meterNumber, String address) {
        this.customerCode=customerCode; this.name=name; this.mobile=mobile;
        this.connectionType=connectionType; this.meterNumber=meterNumber; this.address=address;
    }
    public Long getId(){return id;}
    public String getCustomerCode(){return customerCode;}
    public void setCustomerCode(String v){customerCode=v;}
    public String getName(){return name;}
    public void setName(String v){name=v;}
    public String getMobile(){return mobile;}
    public void setMobile(String v){mobile=v;}
    public String getConnectionType(){return connectionType;}
    public void setConnectionType(String v){connectionType=v;}
    public String getMeterNumber(){return meterNumber;}
    public void setMeterNumber(String v){meterNumber=v;}
    public String getAddress(){return address;}
    public void setAddress(String v){address=v;}
    public List<Bill> getBills(){return bills;}
}
