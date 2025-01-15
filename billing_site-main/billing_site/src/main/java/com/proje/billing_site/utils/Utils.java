package com.proje.billing_site.utils;

import com.proje.billing_site.entity.Payments;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Utils {

    /**
     * java.util.Date'i java.time.LocalDate'e dönüştürür
     *
     * @param date java.util.Date türünde tarih
     * @return LocalDate türünde tarih
     */
    public static LocalDate convertToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Örnek bir yardımcı yöntem: Fatura bilgilerini işleme
     *
     * @param payments Fatura nesnesi
     */
    public static void processPayment(Payments payments) {
        if (payments == null) {
            throw new IllegalArgumentException("Payments nesnesi null olamaz.");
        }

        // Örnek işlem: invoiceID al
        Integer invoiceId = payments.getInvoiceID();
        System.out.println("Invoice ID: " + invoiceId);

        // Tarihleri dönüştür ve yazdır
        LocalDate startDate = convertToLocalDate(payments.getDateStart());
        LocalDate endDate = convertToLocalDate(payments.getDateEnd());

        System.out.println("Fatura Başlangıç Tarihi: " + startDate);
        System.out.println("Fatura Bitiş Tarihi: " + endDate);
    }
}


