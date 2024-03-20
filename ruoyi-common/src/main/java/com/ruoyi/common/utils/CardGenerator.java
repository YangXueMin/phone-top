package com.ruoyi.common.utils;

import java.util.Random;

/**
 * @author ruoyi
 * @ClassName CardGenerator
 * @Description
 * @date 2024/3/5 7:03 PM
 */
public class CardGenerator {
    private static final String CHARACTERS = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char c = CHARACTERS.charAt(index);
            sb.append(c);
        }
        return sb.toString();
    }

    public static boolean containsInvalidCharacters(String str) {
        return str.contains("0") || str.contains("o") || str.contains("1") || str.contains("i");
    }

    public static String generateCard(int length) {
        String card = generateRandomString(length);
        while (containsInvalidCharacters(card)) {
            card = generateRandomString(length);
        }
        return card;
    }

    public static void main(String[] args) {
        System.out.println(generateCard(12));
    }
}
