package com.bharath.learning.socialmediablog_app_rta_33.repository;

import com.bharath.learning.socialmediablog_app_rta_33.model.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {


    //findByEmail
    Optional<UserEntity> findByEmail(String email);

    //findByUsername
    Optional<UserEntity> findByUsername(String username);

    //findByUsernameOrEmail
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    //existsByUsername
    Boolean existsByUsername(String username);

    //existByEmail
    Boolean existsByEmail(String email);


}
