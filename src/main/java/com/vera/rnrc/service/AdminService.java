package com.vera.rnrc.service;


import com.vera.rnrc.dto.authorization.AuthorityDTO;
import com.vera.rnrc.dto.authorization.UserDTO;
import com.vera.rnrc.entity.AuthorityEntity;
import com.vera.rnrc.entity.UserEntity;
import com.vera.rnrc.repository.AuthorityRepository;
import com.vera.rnrc.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final JpaUserRepository jpaUserRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder encoder;

    public UserEntity createUser(UserDTO userDTO) {
        UserEntity user = UserDTO.parseUserDto(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        jpaUserRepository.save(user);
        return user;
    }

    public UserEntity updateUser(UserDTO userDTO) {
        UserEntity user = jpaUserRepository.findById(userDTO.getId()).orElse(null);
        if (user != null) {
            user = UserDTO.parseUserDto(userDTO);
            jpaUserRepository.save(user);
        }
        return user;
    }

    public UserDTO softDeleteUser(Integer id) {
        UserEntity user = jpaUserRepository.findById(id).orElse(null);
        if (user != null) {
            user.setIsDeleted(user.getIsDeleted() == null || !user.getIsDeleted());
            jpaUserRepository.save(user);
        }
        return UserDTO.parseUser(user);
    }

    public void deleteUser(Integer id) {
        jpaUserRepository.findById(id).ifPresent(user -> jpaUserRepository.deleteById(id));
    }

    public AuthorityDTO addAuthority(AuthorityDTO authorityDTO) {
        UserEntity user = jpaUserRepository.findUserByUsername(authorityDTO.getUsername());
        AuthorityEntity authority = new AuthorityEntity();
        authority.setAuthority(authorityDTO.getAuthority());
        authority.setUsername(user);
        authorityRepository.save(authority);
        return AuthorityDTO.parseAuthorityDTO(authority);
    }
}