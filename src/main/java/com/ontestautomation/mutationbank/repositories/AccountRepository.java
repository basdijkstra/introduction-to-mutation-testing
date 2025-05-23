package com.ontestautomation.mutationbank.repositories;

import com.ontestautomation.mutationbank.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
