package com.microservices.account.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Accounts extends BaseEntity{

    @Column
    private Long customerId;
    @Id
    private Long accountNumber;
    @Column
    private String accountType;
    @Column
    private String branchAddress;
}
