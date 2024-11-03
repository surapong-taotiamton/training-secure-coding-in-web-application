package com.example.clientinjection.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    private String userId;
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String companyId;
}
