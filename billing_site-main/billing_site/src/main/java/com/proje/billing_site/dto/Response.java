package com.proje.billing_site.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int statusCode;
    private String message;

    private String token;
    private String role;
    private String expirationTime;
    private String paidConfirmationCode;

    private UserDTO user;
    private PaymentsDTO payment;
    private List<UserDTO> userList;
    private List<PaymentsDTO> roomList;

}
