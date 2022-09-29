package ru.rsreu.app.dto;

import ru.rsreu.app.entity.Role;
import ru.rsreu.app.entity.User;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.rsreu.app.validation.PasswordMatches;
import ru.rsreu.app.validation.ValidEmail;
import ru.rsreu.app.validation.ValidPassword;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@PasswordMatches
@Data
@Service
public class UserDto {
    @NotNull
    @Size(min = 1, message = "{Size.userDto.firstName}")
    private String firstName;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.lastName}")
    private String lastName;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.username}")
    private String username;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;

    public User toUser() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        Role role = new Role();
        role.setId(2L);
        role.setName("ROLE_USER");
        user.setRoles(Set.of(role));
        return user;
    }
}
