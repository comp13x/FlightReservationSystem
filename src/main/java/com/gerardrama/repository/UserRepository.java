package com.gerardrama.repository;

import com.gerardrama.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    User findUserByFirstName(String firstName);
    User findUserByLastName(String lastName);
}
