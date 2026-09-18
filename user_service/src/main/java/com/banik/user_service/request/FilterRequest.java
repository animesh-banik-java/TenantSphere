package com.banik.user_service.request;


import lombok.Data;

@Data
public class FilterRequest {

    private String name;

    private String email;

    private String phoneNo;

    private String address;


}
