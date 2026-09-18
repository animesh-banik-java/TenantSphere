package com.banik.user_service.request;


import com.banik.user_service.basepackage.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class UserRequestDTO {

    @NotBlank(message = "Name not should be Blank")
    @Size(min = 3, max = 30, message = "Name should be 3 to 30 characters")
    private String name;

    @NotBlank(message = "Email ID not should be Blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Phone Number not should be Blank")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
    private String phoneNo;

    private Date dateOfBirth;

    private String address;

    private Status status = Status.ACTIVE;
}
