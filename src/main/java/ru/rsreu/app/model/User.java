//package ru.rsreu.cookbook.model;
//
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import javax.persistence.*;
//import java.util.Collection;
//
//@Data
//@Entity
//@Table(name = "user_account")
//public class User implements UserDetails {
//
//    @Id
//    @Column(unique = true, nullable = false)
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String firstName;
//
//    private String lastName;
//
//    private String username;
//
//    private String email;
//
//    @Column(length = 60)
//    private String password;
//
//    private String role;
//
//    private boolean enabled;
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return getRole();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//}