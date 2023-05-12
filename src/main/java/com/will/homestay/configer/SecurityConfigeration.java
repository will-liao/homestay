package com.will.homestay.configer;

import com.will.homestay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpRequest;
import java.util.List;

@Configuration
public class SecurityConfigeration {
    @Autowired
    UserService userService;

    @Configuration
    public class SecurityConfig {

        @Autowired
        private AuthenticationConfiguration authenticationConfiguration;
        @Bean
        public AuthenticationManager authenticationManager() throws Exception{
            AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
            return authenticationManager;
        }
    }

    //过滤链配置
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/static/**","/toLogin","/toRegester","/login/doLogin","/homestay/user/register").permitAll()
                        .requestMatchers("/homestay/user/toCommon-user-index").hasRole("COMMON")
                        .requestMatchers("/homestay/user/toLandlord-user-index").hasRole("LANDLORD")
                        .requestMatchers("/homestay/user/toManager-user-index").hasRole("MANAGER")
                        .anyRequest().authenticated())
                .formLogin().loginPage("/toLogin").loginProcessingUrl("/login/doLogin").successHandler(new LoginSuccessHandle())
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/toLogin")
                /*.logout().logoutSuccessUrl("/").and()
                .formLogin().loginPage("/toLogin").loginProcessingUrl("/login").defaultSuccessUrl("/user").failureUrl("/login?error")*/;
        return http.build();
    }

    //用户认证
    @Bean(name = "userDetailsService")
    UserDetailsService getUserService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                //查询用户及密码
                com.will.homestay.entity.User user = userService.selectUserByUsername(username);
                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getUserType());
                return new User(username,user.getPassword(),auths);
                /*List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
                return new User("admin","123",auths);*/
            }
        };
    }


    //密码加密
    @Bean(name = "passwordEncoder")
    PasswordEncoder encoder(){
        return  new BCryptPasswordEncoder();
    }
}
