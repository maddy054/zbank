package com.zbank.utilities;

import java.security.SecureRandom;

public class ApiKeyGenerator {

    private static final String CHAR_SET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int KEY_LENGTH = 32;

    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateApiKey() {
        StringBuilder apiKey = new StringBuilder(KEY_LENGTH);

        for (int i = 0; i < KEY_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(CHAR_SET.length());
            apiKey.append(CHAR_SET.charAt(randomIndex));
        }

        return apiKey.toString();
    }

   
}
