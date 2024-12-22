package com.example.clientinjection.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    // Generate a random salt
    public static String generateSalt() {
        return RandomStringUtils.secureStrong().next(15, true, true);
    }

    // Create a SHA-256 hash of the password combined with the salt
    public static String hashPassword(String salt, String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            String saltedPassword = salt + password;
            byte[] hash = messageDigest.digest(saltedPassword.getBytes());
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Convert byte array to hexadecimal string
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void main(String[] args) {
        String password = "My_P@ssWord_Is_CutePat";

        // Generate a new salt
        String salt = generateSalt();
        System.out.println("Generated Salt: " + salt);

        // Hash the password with the salt
        String hashedPassword = hashPassword(salt, password);
        System.out.println("Hashed Password: " + hashedPassword);

        System.out.println("Salt + Hash = " + salt + hashedPassword);

        // Note: Store the salt and hashed password securely for verification
    }
}