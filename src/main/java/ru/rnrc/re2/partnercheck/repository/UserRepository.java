package ru.rnrc.re2.partnercheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rnrc.re2.partnercheck.entity.User;

import java.util.Optional;


@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);

    Optional<User> findById(Long id);
}