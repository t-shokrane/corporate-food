package com.corporate.food.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPassword {

    public static void main(String[] args) {

        String encodedPassword =
                new BCryptPasswordEncoder()
                        .encode("admin123");

        System.out.println(encodedPassword);
    }
}