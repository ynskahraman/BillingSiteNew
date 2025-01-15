package com.proje.billing_site.controller;

import com.proje.billing_site.dto.LoginRequest;
import com.proje.billing_site.dto.Response;
import com.proje.billing_site.entity.Role;
import com.proje.billing_site.entity.User;
import com.proje.billing_site.repo.UserRepository;
import com.proje.billing_site.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Kullanıcı kaydı
    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody User user) {
        Response response = new Response();
        try {
            // Şifreyi hashle
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Varsayılan role USER olarak ayarlanır
            if (user.getRole() == null) {
                user.setRole(Role.USER);
            }

            // Kullanıcıyı veritabanına kaydet
            userRepository.save(user);

            response.setStatusCode(200);
            response.setMessage("Kullanıcı başarıyla kaydedildi.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Kullanıcı kaydedilirken bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // Kullanıcı girişi
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getMail());
        Response response = new Response();

        // Kullanıcı doğrulama
        if (userOpt.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), userOpt.get().getPassword())) {
            response.setStatusCode(401);
            response.setMessage("Email veya şifre hatalı");
            return ResponseEntity.status(401).body(response);
        }

        User user = userOpt.get();

        // JWT token oluşturma
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        response.setStatusCode(200);
        response.setMessage("Giriş başarılı");
        response.setToken(token);
        response.setRole(user.getRole().name());

        return ResponseEntity.ok(response);
    }

    // Kullanıcıları listeleme (admin için örnek)
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
