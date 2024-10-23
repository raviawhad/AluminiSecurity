package com.humancloud.alumni.alumniportal.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

     @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class LoginRequest {
        String email;
        String password;
    }


