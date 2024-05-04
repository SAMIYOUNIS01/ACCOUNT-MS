package com.microservices.account.repository;

import com.microservices.account.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account , Long> {


    Optional<Account> findByCustomerId(Long CustomerId);
    @Modifying
    @Transactional
    void deleteByCustomerId(Long customerId);
}
