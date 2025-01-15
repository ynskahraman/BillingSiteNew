package com.proje.billing_site.repo;

import com.proje.billing_site.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    // BURDA USER DB SINDE BU QUERY ICINDE MAILI BULUYOR
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}