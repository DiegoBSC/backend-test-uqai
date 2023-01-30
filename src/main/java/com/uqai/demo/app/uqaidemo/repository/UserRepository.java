package com.uqai.demo.app.uqaidemo.repository;

import com.uqai.demo.app.uqaidemo.enums.EnumStatus;
import com.uqai.demo.app.uqaidemo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmailOrUsername(String email, String username);

    Long countByStatus(EnumStatus status);
}