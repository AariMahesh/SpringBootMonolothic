package com.myproject.mono.dto;

import com.myproject.mono.model.UserRole;
import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private UserRole role = UserRole.CUSTOMER;
    private AddressDto addressDto;
}
