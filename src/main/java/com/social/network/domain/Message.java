package com.social.network.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Message {
    private String message;
    private Date date = new Date();
}