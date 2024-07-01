package com.microservices.account.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@Getter
public class Customer extends BaseEntity{
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String mobileNumber;
}
