package com.example.clientinjection.repository;

import com.example.clientinjection.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    Optional<UserInfo> findOneByUsername(String username);

}
