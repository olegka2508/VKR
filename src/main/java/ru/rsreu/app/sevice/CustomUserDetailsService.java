package ru.rsreu.app.sevice;

import ru.rsreu.app.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.rsreu.app.entity.User;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository
                .findByUsername(username);

        if (!user.isPresent()) {// should have proper handling of Exception
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }

        return user.get();
//        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
//                user.get().getRole());
//        UserDetails details = new org.springframework.security.core.userdetails.User(user.get().getUsername(),
//                user.get().getPassword(), Arrays.asList(grantedAuthority));
//
//        return details;
    }

}