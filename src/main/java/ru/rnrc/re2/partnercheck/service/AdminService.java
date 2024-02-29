package ru.rnrc.re2.partnercheck.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rnrc.re2.partnercheck.dto.authorization.AuthorityDTO;
import ru.rnrc.re2.partnercheck.dto.authorization.UserDTO;
import ru.rnrc.re2.partnercheck.entity.Authority;
import ru.rnrc.re2.partnercheck.entity.User;
import ru.rnrc.re2.partnercheck.repository.AuthorityRepository;
import ru.rnrc.re2.partnercheck.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder encoder;
    private static final Logger logger = LogManager.getLogger("jdbc");

    public User createUser(UserDTO userDTO) {
        try {
            User user = UserDTO.parseUserDto(userDTO);
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            logger.error("AdminService class createUser method exception: " + e.getMessage());
        }
        return null;
    }

    public User updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElse(null);
        try {
            if (user != null) {
                user = UserDTO.parseUserDto(userDTO);
                userRepository.save(user);
            }
        } catch (Exception e) {
            logger.error("AdminService class updateUser method exception: " + e.getMessage());
        }
        return user;
    }

    public UserDTO softDeleteUser(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        try {
            if (user != null) {
                user.setIsDeleted(user.getIsDeleted() == null || !user.getIsDeleted());
                userRepository.save(user);
            }
        } catch (Exception e) {
            logger.error("AdminService class softDeleteUser method exception: " + e.getMessage());
        }
        return UserDTO.parseUser(user);
    }

    public AuthorityDTO addAuthority(AuthorityDTO authorityDTO) {
        try {
            User user = userRepository.findUserByUsername(authorityDTO.getUsername());
            Authority authority = new Authority();
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