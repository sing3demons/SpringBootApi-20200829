package com.sing3demons.springbootapi.business;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import com.sing3demons.springbootapi.exception.BaseException;
import com.sing3demons.springbootapi.exception.EmailException;
import com.sing3demons.springbootapi.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailBusiness {
    private final EmailService emailService;

    public void sendActivateUserEmail(String email, String name, String token) throws BaseException {
        String html;
        try {
            html = readEmailTemplate("email-activate-user.html");
        } catch (IOException e) {

            throw EmailException.templateNotFound();
        }

        String finalLink = "http://localhost:4200/activate/" + token;
        html = html.replace("${P_NAME}", name);
        html = html.replace("${LINK}", finalLink);

        String subject = "Please activate your account";
        emailService.send(email, subject, html);
    }

    private String readEmailTemplate(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:email/" + filename);
        return FileCopyUtils.copyToString(new FileReader(file));
    }
}
