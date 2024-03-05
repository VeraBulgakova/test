package ru.rnrc.re2.partnercheck.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.rnrc.re2.partnercheck.dto.authorization.UserDTO;
import ru.rnrc.re2.partnercheck.entity.User;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder encoder;

    public User parseUserDto(UserDTO userDTO, User user) {
        Optional.ofNullable(userDTO).ifPresent(dto -> {
            Optional.ofNullable(dto.getId()).ifPresent(user::setId);
            Optional.ofNullable(dto.getUsername()).ifPresent(user::setUsername);

            Optional.ofNullable(dto.getPassword()).filter(pass -> !encoder.matches(pass, user.getPassword()))
                    .map(encoder::encode).ifPresent(user::setPassword);

            Optional.ofNullable(dto.getEnabled()).ifPresent(user::setEnabled);
            Optional.ofNullable(dto.getPriority()).ifPresent(user::setPriority);
            Optional.ofNullable(dto.getEmail()).ifPresent(user::setEmail);
            Optional.ofNullable(dto.getIsDeleted()).ifPresent(user::setIsDeleted);
        });
        return user;
    }

    public User parseUserDto(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setEnabled(userDTO.getEnabled());
        user.setEmail(userDTO.getEmail());
        user.setPriority(userDTO.getPriority());
        user.setIsDeleted(userDTO.getIsDeleted());
        return user;
    }
}
