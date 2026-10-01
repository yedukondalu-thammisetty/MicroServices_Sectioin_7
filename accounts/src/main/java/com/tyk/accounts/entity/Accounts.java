package com.tyk.accounts.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Accounts extends BaseEntity{

    @Id
    private Long accountNumber;

    private Long customerId;

    private String accountType;

    @Column(name="branch_address")
    private String branchAddress;

}
