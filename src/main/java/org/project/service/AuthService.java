package org.project.service;

import org.project.exception.ValidationException;
import org.project.model.User;
import org.project.model.dto.LoginRequest;
import org.project.model.dto.LoginResponse;
import org.project.util.FormatUtil;
import org.project.util.GeneralUtil;
import org.project.util.JwtUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@ApplicationScoped
public class AuthService {
    public Response login(LoginRequest request) throws NoSuchAlgorithmException {
        if (!FormatUtil.isPassword(request.password)){
            throw new ValidationException("INVALID_PASSWORD");
        }
        Optional<User> userOptional = User.findByLoginName(request.loginName);
        if(userOptional.isEmpty()){
            throw new ValidationException("USER_NOT_FOUND");
        }
        User user = userOptional.get();
        if (!user.comparePassword(GeneralUtil.hashPassword(request.password))){
            throw new ValidationException("WRONG_PASSWORD");
        }

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.userData = user;
        loginResponse.token = JwtUtil.generateToken(user);

        return Response.ok(loginResponse).build();
    }
}
