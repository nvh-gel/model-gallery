package com.eden.gallery.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Strings {

    public static final String LIKE_PATTERN = "%%%s%%";
    public static final String SEND_MESSAGE_TO_TOPIC = "Send message {} to topic {}";
    public static final String RECEIVED_MESSAGE_FROM_TOPIC = "Received message {} from topic {}";
}
