package com.vera.rnrc.dto.authorization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vera.rnrc.entity.UserEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    @Id
    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
    private String email;
    private String lastActivity;
    private Integer priority;
    private Boolean isDeleted;

    public static UserDTO parseUser(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        if (user != null) {
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTO.setEnabled(user.getEnabled());
            userDTO.setEmail(user.getEmail());
            userDTO.setPriority(user.getPriority());
            userDTO.setIsDeleted(user.getIsDeleted());
        }
        return userDTO;
    }

    public static UserEntity parseUserDto(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        if (userDTO != null) {
            user.setId(userDTO.getId());
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setEnabled(userDTO.getEnabled());
            user.setEmail(userDTO.getEmail());
            user.setPriority(userDTO.getPriority());
            user.setIsDeleted(userDTO.getIsDeleted());
        }
        return user;
    }
}