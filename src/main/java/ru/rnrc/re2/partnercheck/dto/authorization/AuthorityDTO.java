package ru.rnrc.re2.partnercheck.dto.authorization;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.rnrc.re2.partnercheck.entity.Authority;

@Data
public class AuthorityDTO {
    @Schema(type = "string", example = "user")
    private String username;
    @Schema(type = "string", example = "USER")
    private String authority;

    public static AuthorityDTO parseAuthorityDTO(Authority authority) {
        AuthorityDTO authorityDTO = new AuthorityDTO();
        if (authority != null) {
            authorityDTO.setUsername(authority.getUsername().getUsername());
            authorityDTO.setAuthority(authority.getAuthority());
        }
        return authorityDTO;
    }
}
