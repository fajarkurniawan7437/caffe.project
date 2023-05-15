package org.project.service;

import io.quarkus.narayana.jta.runtime.TransactionConfiguration;
import org.project.exception.ValidationException;
import org.project.model.User;
import org.project.model.dto.UserRequest;
import org.project.util.FormatUtil;
import org.project.util.GeneralUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Optional;

@ApplicationScoped
public class UserService {
    public Response post(UserRequest request) throws NoSuchAlgorithmException {
        if(!User.isEmptyByLoginName(request.loginName)){
            throw new ValidationException("LOGIN_NAME_EXISTS");
        }
        if (!FormatUtil.isPassword(request.password)){
            throw new ValidationException("INVALID_PASSWORD");
        }
        if (!FormatUtil.isEmail(request.email) || !FormatUtil.isAlphabet(request.fullName)
            || !FormatUtil.isPhoneNumber(request.mobilePhoneNumber) || !FormatUtil.isPhoneNumber(request.workPhoneNumber)){
            throw new ValidationException("BAD_REQUEST");
        }
        persistUser(request, request.loginName);

        return Response.ok(new HashMap<>()).build();
    }

    public Response put(UserRequest request, String userId) throws NoSuchAlgorithmException {
        if (!FormatUtil.isPassword(request.password)){
            throw new ValidationException("INVALID_PASSWORD");
        }
        if (!FormatUtil.isEmail(request.email) || !FormatUtil.isAlphabet(request.fullName)
                || !FormatUtil.isPhoneNumber(request.mobilePhoneNumber) || !FormatUtil.isPhoneNumber(request.workPhoneNumber)){
            throw new ValidationException("BAD_REQUEST");
        }
        persistUser(request,userId);

        return Response.ok(new HashMap<>()).build();
    }


    public Response get(String loginName){
        Optional<User> userOptional = User.findByLoginName(loginName);
        if (userOptional.isEmpty()) {
            throw new ValidationException("USER_NOT_FOUND");
        }
        return Response.ok(userOptional.get()).build();
    }

    @Transactional
    @TransactionConfiguration(timeout = 30)
    public Response delete(String userId){
        User.deleteById(userId);
        return Response.ok(new HashMap<>()).build();
    }



    @Transactional
    @TransactionConfiguration(timeout = 30)
    public User persistUser(UserRequest request, String userId) throws NoSuchAlgorithmException {
        User user;
        if (userId == null){
            user = new User();
        }else {
            Optional<User> userOptional = User.findByIdOptional(userId);
            if (userOptional.isEmpty()){
                throw new ValidationException("USER_NOT_FOUND");
            }
            user = userOptional.get();
        }
        user.setLoginName(request.loginName);
        user.setPassword(GeneralUtil.hashPassword(request.password));
        user.setAddress(request.address);
        user.setFullName(request.fullName);
        user.setEmail(request.email);
        user.setMobilePhoneNumber(request.mobilePhoneNumber);
        user.setWorkPhoneNumber(request.workPhoneNumber);
        User.persist(user);

        return user;
    }

}
