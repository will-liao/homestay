package com.will.homestay.configer;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

class LoginSuccessHandle implements AuthenticationSuccessHandler {
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication){

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        try {
            if (roles.contains("ROLE_MANAGER")) {
                response.sendRedirect("/homestay/user/toManager-user-index");
                return;
            }if (roles.contains("ROLE_LANDLORD")){
                response.sendRedirect("/homestay/user/toLandlord-user-index");
                return;
            }
                response.sendRedirect("/homestay/user/toCommon-user-index");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
