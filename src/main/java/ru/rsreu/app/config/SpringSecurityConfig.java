package ru.rsreu.app.config;

import ru.rsreu.app.sevice.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableWebSecurity
//@ComponentScan
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .addFilterAfter(siteminderFilter(), RequestHeaderAuthenticationFilter.class).authorizeRequests()

//                .antMatchers("/h2-console/**").anonymous()
                .antMatchers("/user/registration").anonymous()
                .antMatchers("/user/add").anonymous()
                .antMatchers("/api/quiz/*").hasAnyRole("USER","ADMIN")
//                .antMatchers("/api/**").hasRole("USER")
//                .antMatchers("/api/quiz/findAll").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .permitAll()
                    .loginPage("/login")
                    .defaultSuccessUrl("/api/quiz/findAll")
                .and()
                    .logout()
                    .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

//    @Bean
//    UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper() throws Exception {
//        UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<>();
//        wrapper.setUserDetailsService(customUserDetailsService);
//        return wrapper;
//    }
//
//
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        List<UserDetails> userDetailsList = new ArrayList<>();
//        userDetailsList.add(User.withUsername("employee").password(passwordEncoder().encode("password"))
//                .roles("EMPLOYEE", "USER").build());
//        userDetailsList.add(User.withUsername("manager").password(passwordEncoder().encode("password"))
//                .roles("MANAGER", "USER").build());
//
//        return new InMemoryUserDetailsManager(userDetailsList);
//    }
//
//    @Bean(name = "preAuthProvider")
//    PreAuthenticatedAuthenticationProvider preauthAuthProvider() throws Exception {
//        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
//        provider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());
//        return provider;
//    }
//
  //  @Bean
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        final List<AuthenticationProvider> providers = new ArrayList<>(1);
//        providers.add(preauthAuthProvider());
//        return new ProviderManager(providers);
//    }
//
//    @Bean(name = "siteminderFilter")
//    public BasicAuthenticationFilter siteminderFilter() throws Exception {
//        BasicAuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager());
//        return requestHeaderAuthenticationFilter;
//    }
}
