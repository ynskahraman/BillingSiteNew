package com.proje.billing_site.service;

import com.proje.billing_site.entity.Payments;
import com.proje.billing_site.entity.User;
import com.proje.billing_site.repo.PaymentsRepository;
import com.proje.billing_site.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private UserRepository userRepository;

    // Kullanıcıya fatura oluşturma
    public Payments createPayment(Payments payment, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + userId));

        payment.setUser(user);
        return paymentsRepository.save(payment);
    }

    // Kullanıcının tüm faturalarını listeleme
    public List<Payments> getPaymentsByUser(Long userId) {
        return paymentsRepository.findByUserId(userId);
    }

    // Belirli bir faturayı ID ile getirme
    public Payments getPaymentById(Long paymentId) {
        return paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Fatura bulunamadı: " + paymentId));
    }

    // Fatura ödeme işlemi
    public Payments payInvoice(Long paymentId) {
        Payments payment = paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Fatura bulunamadı: " + paymentId));

        if ("Ödendi".equalsIgnoreCase(payment.getStatus())) {
            throw new RuntimeException("Bu fatura zaten ödendi.");
        }

        payment.setStatus("Ödendi");
        payment.setPaidConfirmationCode("CONF" + System.currentTimeMillis()); // Ödeme onay kodu oluşturma
        return paymentsRepository.save(payment);
    }

    // Tüm faturaları listeleme
    public List<Payments> getAllPayments() {
        return paymentsRepository.findAll();
    }
}


