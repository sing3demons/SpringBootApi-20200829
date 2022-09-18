package com.sing3demons.springbootapi.business;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

import com.sing3demons.springbootapi.entity.User;
import com.sing3demons.springbootapi.exception.BaseException;
import com.sing3demons.springbootapi.exception.FileException;
import com.sing3demons.springbootapi.exception.UserException;
import com.sing3demons.springbootapi.model.ActivateRequest;
import com.sing3demons.springbootapi.model.ActivateResponse;
import com.sing3demons.springbootapi.model.LoginRequest;
import com.sing3demons.springbootapi.model.LoginResponse;
import com.sing3demons.springbootapi.model.MRegisterRequest;
import com.sing3demons.springbootapi.model.ResendActivationEmailRequest;
import com.sing3demons.springbootapi.service.TokenService;
import com.sing3demons.springbootapi.service.UserService;
import com.sing3demons.springbootapi.util.SecurityUtil;

import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserBusiness {
    private final UserService userService;
    private final TokenService tokenService;
    private final EmailBusiness emailBusiness;

    public User register(MRegisterRequest request) throws UserException {
        String token = SecurityUtil.generateToken();
        User user = userService.create(request.getEmail(), request.getPassword(), request.getName(), token);
        sendEmail(user);
        return user;
    }

    private void sendEmail(User user) {

        String token = user.getToken();
        try {
            emailBusiness.sendActivateUserEmail(user.getEmail(), user.getName(), token);
        } catch (BaseException e) {
            e.printStackTrace();
        }

    }

    public ActivateResponse activate(ActivateRequest request) throws BaseException {
        String token = request.getToken();
        if (StringUtil.isNullOrEmpty(token)) {
            throw UserException.activateNoToken();
        }

        Optional<User> opt = userService.findByToken(token);
        if (opt.isEmpty()) {
            throw UserException.activateFail();
        }

        User user = opt.get();

        if (user.isActivated()) {
            throw UserException.activateAlready();
        }
        
        Date now = new Date();
        Date expireDate = user.getTokenExpire();
        if (now.after(expireDate)) {
            // re-email
            throw UserException.activateTokenExpire();
        }

        user.setActivated(true);

        userService.updateUser(user);
        ActivateResponse response = new ActivateResponse();
        response.setSuccess(true);
        return response;

    }

    public LoginResponse login(LoginRequest request) throws UserException {
        Optional<User> opt = userService.findByEmail(request.getEmail());
        if (opt.isEmpty()) {
            // throw login fail, email not found
            throw UserException.loginFailEmailNotFound();
        }

        User user = opt.get();

        if (!userService.matchPassword(request.getPassword(), user.getPassword())) {
            // throw login fail, password incorrect
            throw UserException.loginFailPasswordIncorrect();
        }

        // verify activate status
        if (!user.isActivated()) {
            throw UserException.loginFailUserUnactivated();
        }

        LoginResponse response = new LoginResponse();
        String token = tokenService.tokenize(user);
        response.setToken(token);

        return response;
    }

    public void resendActivationEmail(ResendActivationEmailRequest request) throws BaseException {
        String email = request.getEmail();
        if (StringUtil.isNullOrEmpty(email)) {
            throw UserException.resendActivationEmailNoEmail();
        }

        Optional<User> opt = userService.findByEmail(email);
        if (opt.isEmpty()) {
            throw UserException.resendActivationEmailNotFound();
        }

        User user = opt.get();

        if (user.isActivated()) {
            throw UserException.activateAlready();
        }

        user.setToken(SecurityUtil.generateToken());
        user.setTokenExpire(nextXMinute(30));
        user = userService.updateUser(user);

        sendEmail(user);
    }

    private Date nextXMinute(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public String refreshToken() throws UserException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findByID(userId);
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }
        User user = optUser.get();
        return tokenService.tokenize(user);
    }

    public String uploadProfilePicture(MultipartFile file) throws BaseException {
        if (file == null) {
            throw FileException.fileNull();
        }

        if (file.getSize() > 1048576 * 2) {
            throw FileException.fileMaxSize();
        }
        String contentType = file.getContentType();
        if (contentType == null) {
            throw FileException.fileUnsupported();
        }

        List<String> supportedTypes = Arrays.asList("image/jpeg", "image/png");
        if (!supportedTypes.contains(contentType)) {
            throw FileException.fileUnsupported();
        }

        return "";
    }

}
