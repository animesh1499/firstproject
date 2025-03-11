package org.example.firstproject.interceptors;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.firstproject.modals.Users;
import org.example.firstproject.repositories.UserDAO;
import org.example.firstproject.services.impl.JWTServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserDAO userDAO;

    @Autowired
    private JWTServicesImpl jwtServices;

    public OAuth2LoginSuccessHandler(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("This is the session id : {} ",request.getSession(false).getId());
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        if(Objects.isNull(userDAO.findByEmail(email))){
            Users newUser = new Users();
            newUser.setEmail(email);
            newUser.setAuthProvider(Users.AuthProvider.GOOGLE);
            newUser.setUsername(email);
            newUser.setPassword(null);
            userDAO.save(newUser);
        }
        String token = jwtServices.generateToken(email);
        log.info("OAuth2 token : {}", token);

        response.setHeader("Authorization", "Bearer " + token);

        getRedirectStrategy().sendRedirect(request, response, "/student/getStudentDetails?course=Biology");
    }
}
