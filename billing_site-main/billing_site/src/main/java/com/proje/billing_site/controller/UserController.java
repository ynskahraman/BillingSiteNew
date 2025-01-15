package com.proje.billing_site.controller;

import com.proje.billing_site.dto.UserDTO;
import com.proje.billing_site.entity.User;
import com.proje.billing_site.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Kullanıcıları listele
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // Kullanıcı bilgisi al
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Kullanıcı faturalarını listele
    @GetMapping("/{id}/invoices")
    public ResponseEntity<?> getUserInvoices(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(user.getPayments()))
                .orElse(ResponseEntity.notFound().build());
    }
}
