package com.bharath.learning.socialmediablog_app_rta_33.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodingDecodingUtility {


    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("Bharath:: "+ passwordEncoder.encode("Guru@421"));
        System.out.println("Admin:: "+ passwordEncoder.encode("admin"));
    }
}
