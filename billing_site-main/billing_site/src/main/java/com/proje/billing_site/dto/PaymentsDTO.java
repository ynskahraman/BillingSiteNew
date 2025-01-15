package com.proje.billing_site.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

///
/// BURDA PAYMENTS IN BILGILERINI WEB E ATMA OLAYI
///

public class PaymentsDTO {

    private Long id;

    private Integer invoiceID;

    private String type;
    private Float price;
    private String status;

    private LocalDate DateStart;
    private LocalDate DateEnd;

    private String paidConfirmationCode;

    private UserDTO user;
}
