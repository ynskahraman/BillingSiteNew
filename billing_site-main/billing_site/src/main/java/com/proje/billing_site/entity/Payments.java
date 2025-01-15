package com.proje.billing_site.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // Örneğin: "Elektrik Faturası"
    private Float price; // Ödeme miktarı
    private String status; // "Ödenmedi" veya "Ödendi"
    private Integer invoiceID; // Fatura ID'si

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String paidConfirmationCode;

    @Temporal(TemporalType.DATE)
    private Date dateStart; // Fatura başlangıç tarihi

    @Temporal(TemporalType.DATE)
    private Date dateEnd; // Fatura bitiş tarihi
}



