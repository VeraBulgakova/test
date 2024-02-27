package com.vera.rnrc.dto.authorization;

import com.vera.rnrc.entity.AuthorityEntity;
import lombok.Data;

@Data
public class AuthorityDTO {
    private String username;
    private String authority;

    public static AuthorityDTO parseAuthorityDTO(AuthorityEntity authority) {
        AuthorityDTO authorityDTO = new AuthorityDTO();
        if (authority != null) {
            authorityDTO.setUsername(authority.getUsername().getUsername());
            authorityDTO.setAuthority(authority.getAuthority());
        }
        return authorityDTO;
    }
}
