package com.datn.ecm.security;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Component
public class CustomSuccessAuthHandler implements AuthenticationSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomSuccessAuthHandler.class);

    /*@Autowired
    private CartService cartService;*/

    /*@Autowired
    private HttpSession session;*/

    @Getter
    @Setter
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        handle(httpServletRequest, httpServletResponse, authentication);
        clearAuthenticationAttribute(httpServletRequest);
    }

    private void clearAuthenticationAttribute(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    private void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
                                                                        throws IOException {
        String targetURL = determineTargetURL(httpServletRequest, authentication);

        if(httpServletResponse.isCommitted()) {
            LOG.debug("Response has already been committed. Unable to redirect to " + targetURL);
            return;
        }

        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetURL);
    }

    private String determineTargetURL(HttpServletRequest httpServletRequest, Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                isUser = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }

        if (isUser) {
            //updateOwnerId(authentication.getPrincipal());
            return "/";
        } else if (isAdmin) {
            return "/admin";
        } else {
            throw new IllegalStateException();
        }
    }

    /*private void updateOwnerId(Object principal) {
        String customerId = "";
        if (principal instanceof CustomUserDetails) {
            customerId = ((CustomUserDetails) principal).getCustomer().getId();
        }
        cartService.updateOwnerId(session.getId(), customerId);
    }*/

}
