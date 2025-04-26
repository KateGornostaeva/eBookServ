package ru.kate.ebook.ebookserv.controllers.dtos;

import lombok.Data;
import ru.kate.ebook.ebookserv.security.Role;

@Data
public class ProfileDto {
    private String username;
    private String email;
    private Role role;
    private String name;
    private String lastName;
    private String middleName;
}
