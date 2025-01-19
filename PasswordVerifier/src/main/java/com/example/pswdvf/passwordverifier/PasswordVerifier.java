package com.example.pswdvf.passwordverifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PasswordVerifier {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String[] passwords = {
                "secureAdminPass123",
                "secureProfPass123",
       "secureProfPass456",
        "secureStudentPass123",
        "secureStudentPass456",
                "stud123", "prof123","adnin",
                "dbownerpassword",
                "readwritepassword"
        };

        for (String password : passwords) {
            String hashedPassword = encoder.encode(password);
            System.out.println("Plaintext: " + password);
            System.out.println("Hashed: " + hashedPassword);
            System.out.println();
        }
    }
}
