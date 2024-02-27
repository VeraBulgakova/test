package com.vera.rnrc.repository;

import com.vera.rnrc.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("jpaUserRepository")
public interface JpaUserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findUserByUsername(String username);
}