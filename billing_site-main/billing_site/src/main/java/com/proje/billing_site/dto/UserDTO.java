package com.proje.billing_site.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

///
/// BURDA USERS IN BILGILERINI WEB E ATMA OLAYI
///
public class UserDTO {

    private Long id;

    private String email;
    private String name;
    private String password;

    private Float balance;
    private String role;

    private List<PaymentsDTO> payments = new ArrayList<>();

}