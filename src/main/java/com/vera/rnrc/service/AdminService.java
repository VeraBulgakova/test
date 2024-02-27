package com.vera.rnrc.service;


import com.vera.rnrc.dto.authorization.AuthorityDTO;
import com.vera.rnrc.dto.authorization.UserDTO;
import com.vera.rnrc.entity.AuthorityEntity;
import com.vera.rnrc.entity.UserEntity;
import com.vera.rnrc.repository.AuthorityRepository;
import com.vera.rnrc.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final JpaUserRepository jpaUserRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder encoder;
    private static final Logger logger = LogManager.getLogger("jdbc");

    public UserEntity createUser(UserDTO userDTO) {
        try {
            UserEntity user = UserDTO.parseUserDto(userDTO);
            user.setPassword(encoder.encode(user.getPassword()));
            jpaUserRepository.save(user);
            return user;
        } catch (Exception e) {
            logger.error("AdminService class createUser method exception: " + e.getMessage());
        }
        return null;
    }

    public UserEntity updateUser(UserDTO userDTO) {
        UserEntity user = jpaUserRepository.findById(userDTO.getId()).orElse(null);
        try {
            if (user != null) {
                user = UserDTO.parseUserDto(userDTO);
                jpaUserRepository.save(user);
            }
        } catch (Exception e) {
            logger.error("AdminService class updateUser method exception: " + e.getMessage());
        }
        return user;
    }

    public UserDTO softDeleteUser(Integer id) {
        UserEntity user = jpaUserRepository.findById(id).orElse(null);
        try {
            if (user != null) {
                user.setIsDeleted(user.getIsDeleted() == null || !user.getIsDeleted());
                jpaUserRepository.save(user);
            }
        } catch (Exception e) {
            logger.error("AdminService class softDeleteUser method exception: " + e.getMessage());
        }
        return UserDTO.parseUser(user);
    }

    public void deleteUser(Integer id) {
        try {
            jpaUserRepository.findById(id).ifPresent(user -> jpaUserRepository.deleteById(id));
        } catch (Exception e) {
            logger.error("AdminService class deleteUser method exception: " + e.getMessage());
        }

    }

    public AuthorityDTO addAuthority(AuthorityDTO authorityDTO) {
        try {
            UserEntity user = jpaUserRepository.findUserByUsername(authorityDTO.getUsername());
            AuthorityEntity authority = new AuthorityEntity();
            authority.setAuthority(authorityDTO.getAuthority());
            authority.setUsername(user);
            authorityRepository.save(authority);
            return AuthorityDTO.parseAuthorityDTO(authority);
        } catch (Exception e) {
            logger.error("AdminService class addAuthority method exception: " + e.getMessage());
        }
        return null;
    }
}