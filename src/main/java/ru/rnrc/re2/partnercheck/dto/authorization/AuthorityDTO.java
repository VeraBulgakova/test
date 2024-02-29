package ru.rnrc.re2.partnercheck.dto.authorization;

import lombok.Data;
import ru.rnrc.re2.partnercheck.entity.Authority;

@Data
public class AuthorityDTO {
    private String username;
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
