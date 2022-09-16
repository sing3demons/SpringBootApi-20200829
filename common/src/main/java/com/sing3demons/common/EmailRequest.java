package com.sing3demons.common;

import lombok.Data;

@Data
public class EmailRequest {
    private  String to;
    private String content;
    private String subject;
}
