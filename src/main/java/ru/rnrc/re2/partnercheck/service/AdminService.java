package ru.rnrc.re2.partnercheck.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.rnrc.re2.partnercheck.dto.authorization.AuthorityDTO;
import ru.rnrc.re2.partnercheck.dto.authorization.UserDTO;
import ru.rnrc.re2.partnercheck.entity.Authority;
import ru.rnrc.re2.partnercheck.entity.User;
import ru.rnrc.re2.partnercheck.mapper.UserMapper;
import ru.rnrc.re2.partnercheck.repository.AuthorityRepository;
import ru.rnrc.re2.partnercheck.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserMapper userMapper;
    private static final Logger errorLogger = LoggerFactory.getLogger(AdminService.class);

    public User createUser(UserDTO userDTO) {
        try {
            User user = userMapper.parseUserDto(userDTO);

            Authority authority = new Authority();
            authority.setUsername(user);
            authority.setAuthority("ROLE_USER");
            authorityRepository.save(authority);

            userRepository.save(user);
            return user;
        } catch (Exception e) {
            errorLogger.error("AdminService class createUser method exception: " + e.getMessage());
        }
        return null;
    }

    public User updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElse(null);
        try {
            if (user != null) {
                user = userMapper.parseUserDto(userDTO, user);
                userRepository.save(user);
            }
        } catch (Exception e) {
            errorLogger.error("AdminService class updateUser method exception: " + e.getMessage());
        }
        return user;
    }

    public void softDeleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        try {
            if (user != null) {
                user.setIsDeleted(user.getIsDeleted() == null || !user.getIsDeleted());
                userRepository.save(user);
            }
        } catch (Exception e) {
            errorLogger.error("AdminService class softDeleteUser method exception: " + e.getMessage());
        }
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
            errorLogger.error("AdminService class addAuthority method exception: " + e.getMessage());
        }
        return null;
    }
}