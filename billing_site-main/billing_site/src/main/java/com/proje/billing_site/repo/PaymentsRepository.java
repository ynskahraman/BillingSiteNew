package com.proje.billing_site.repo;

import com.proje.billing_site.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
    List<Payments> findByUserId(Long userId);
}
