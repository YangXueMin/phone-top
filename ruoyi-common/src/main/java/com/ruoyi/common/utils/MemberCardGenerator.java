package com.ruoyi.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author yangxuemin
 * @ClassName MemberCardGenerator
 * @Description
 * @date 2024/1/4 6:30 PM
 */
public class MemberCardGenerator {
    private static final String DATE_FORMAT = "yyyyMMddHHmmss";
    private static final int RANDOM_RANGE = 100000;

    public static String generateMemberCard() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String timestamp = dateFormat.format(new Date());

        Random random = new Random();
        int randomNumber = random.nextInt(RANDOM_RANGE);

        return timestamp + String.format("%05d", randomNumber);
    }
}
