package com.ecom.app.dto;

import com.ecom.app.model.UserRole;
import lombok.Data;

@Data
public class UserRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private AddressDTO address;
}
