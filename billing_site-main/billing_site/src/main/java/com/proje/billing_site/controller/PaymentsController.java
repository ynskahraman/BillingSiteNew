package com.proje.billing_site.controller;

import com.proje.billing_site.entity.Payments;
import com.proje.billing_site.entity.User;
import com.proje.billing_site.repo.PaymentsRepository;
import com.proje.billing_site.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Tüm faturaları listeleme
     */
    @GetMapping
    public ResponseEntity<List<Payments>> getAllPayments() {
        return ResponseEntity.ok(paymentsRepository.findAll());
    }

    /**
     * Kullanıcı ID'sine göre faturaları listeleme
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payments>> getPaymentsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentsRepository.findByUserId(userId));
    }

    /**
     * Belirli bir kullanıcı için fatura oluşturma
     */
    @PostMapping("/create/{userId}")
    public ResponseEntity<Payments> createPaymentForUser(@PathVariable Long userId, @RequestBody Payments payment) {
        // Kullanıcıyı bul
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + userId));

        // Faturayı kullanıcıya bağla
        payment.setUser(user);
        payment.setStatus("Ödenmedi"); // Varsayılan durum
        Payments savedPayment = paymentsRepository.save(payment);

        return ResponseEntity.ok(savedPayment); // Yanıt döndür
    }




    /**
     * Fatura ID'sine göre ödeme yapma
     */
    @PutMapping("/{paymentId}/pay")
    public ResponseEntity<Payments> payInvoice(@PathVariable Long paymentId) {
        // Faturayı bul
        Payments payment = paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Fatura bulunamadı: " + paymentId));

        // Fatura durumunu "Ödendi" olarak değiştir
        payment.setStatus("Ödendi");
        payment.setPaidConfirmationCode("CONF-" + paymentId); // Ödeme onay kodu
        Payments updatedPayment = paymentsRepository.save(payment);

        return ResponseEntity.ok(updatedPayment);
    }
}

