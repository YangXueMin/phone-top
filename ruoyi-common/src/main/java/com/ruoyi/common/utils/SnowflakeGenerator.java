package com.ruoyi.common.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

/**
 * @author yangxuemin
 * @ClassName MemberCardGenerator
 * @Description
 * @date 2024/1/4 6:30 PM
 */
public class SnowflakeGenerator {
    private static final String DATE_FORMAT = "yyyyMMddHHmmss";
    private static final int RANDOM_RANGE = 100000;

    private static final int datacenterIdBits = 5;
    private static final int machineIdBits = 5;
    private static final int sequenceBits = 12;

    private static final long maxDatacenterId = ~(-1L << datacenterIdBits);
    private static final long maxMachineId = ~(-1L << machineIdBits);
    private static final long maxSequence = ~(-1L << sequenceBits);

    private static final long machineIdShift = sequenceBits;
    private static final long datacenterIdShift = sequenceBits + machineIdBits;
    private static final long timestampShift = sequenceBits + machineIdBits + datacenterIdBits;

    // 2024-01-10T10:48:22.386
    private static final long twepoch = 1704854902387L;
    private static long datacenterId = 1L;
    private static long machineId = 1L;
    private static long sequence = 0L;
    private static long lastTimestamp = -1L;

    public static String generateOrderNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String timestamp = dateFormat.format(new Date());

        Random random = new Random();
        int randomNumber = random.nextInt(RANDOM_RANGE);

        return timestamp + String.format("%05d", randomNumber);
    }

    public static void setDatacenterId(long datacenterId) {
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than " + maxDatacenterId + " or less than 0");
        }
        SnowflakeGenerator.datacenterId = datacenterId;
    }

    public static void setMachineId(long machineId) {
        if (machineId > maxMachineId || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than " + maxMachineId + " or less than 0");
        }
        SnowflakeGenerator.machineId = machineId;
    }

    public static synchronized Long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampShift) |
                (datacenterId << datacenterIdShift) |
                (machineId << machineIdShift) |
                sequence;
    }

    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private static long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        //SnowflakeGenerator.setDatacenterId(1);
        //SnowflakeGenerator.setMachineId(1);
        //
        //for (int i = 0; i < 10; i++) {
        //    long id = SnowflakeGenerator.nextId();
        //    System.out.println(id);
        //}
        System.out.println(generateOrderNumber());
    }
}
