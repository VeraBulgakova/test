package ru.rnrc.re2.partnercheck.dto.authorization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import ru.rnrc.re2.partnercheck.entity.User;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    @Id
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
    private String email;
    private String lastActivity;
    private Integer priority;
    private Boolean isDeleted;

    public static UserDTO parseUser(User user) {
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

    public static User parseUserDto(UserDTO userDTO) {
        User user = new User();
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