package com.sing3demons.springbootapi.business;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.sing3demons.common.EmailRequest;
import com.sing3demons.springbootapi.exception.BaseException;
import com.sing3demons.springbootapi.exception.EmailException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class EmailBusiness {
    private final KafkaTemplate<String, EmailRequest> kafkaTemplate;

    public void sendActivateUserEmail(String email, String name, String token) throws BaseException {
        String html;
        try {
            html = readEmailTemplate("email-activate-user.html");
        } catch (IOException e) {

            throw EmailException.templateNotFound();
        }

        log.info("token : " + token);

        String finalLink = "http://localhost:4200/activate/" + token;
        html = html.replace("${P_NAME}", name);
        html = html.replace("${LINK}", finalLink);

        String subject = "Please activate your account";

        EmailRequest request = new EmailRequest();
        request.setTo(email);
        request.setSubject(subject);
        request.setContent(html);

        ListenableFuture<SendResult<String, EmailRequest>> future = kafkaTemplate
                .send("activation-email",
                        request);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onFailure(Throwable ex) {
                log.error("kafka send fail");

            }

            @Override
            public void onSuccess(SendResult<String, EmailRequest> result) {
                log.info("Kafka send success");
                log.info(result);

            }

        });

    }

    private String readEmailTemplate(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:email/" + filename);
        return FileCopyUtils.copyToString(new FileReader(file));
    }
}
