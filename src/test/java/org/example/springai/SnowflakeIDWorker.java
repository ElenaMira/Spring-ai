package org.example.springai;

import org.junit.jupiter.api.Test;

public class SnowflakeIDWorker {
    // 起始时间戳（2022-01-01 00:00:00 UTC）
    private static final long START_TIMESTAMP = 1640995200000L;
    // 各部分占用位数
    private static final long SEQUENCE_BIT = 12;  // 序列号占用12位
    private static final long MACHINE_BIT = 5;    // 机器ID占用5位
    private static final long DATA_CENTER_BIT = 5; // 数据中心ID占用5位
    // 各部分的最大值
    private static final long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);
    private static final long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    // 各部分的偏移量
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    private static final long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    private long dataCenterId;  // 数据中心ID
    private long machineId;     // 机器ID
    private long sequence = 0L; // 序列号
    private long lastTimestamp = -1L; // 上一次生成ID的时间戳

    public SnowflakeIDWorker() {
        this(1, 1); // 默认数据中心ID和机器ID都为1
    }

    // 构造函数，初始化数据中心ID和机器ID
    public SnowflakeIDWorker(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("数据中心ID超出范围");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("机器ID超出范围");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }
    // 生成唯一ID
    public synchronized long nextId() {
        long currentTimestamp = getCurrentTimestamp();

        // 如果当前时间小于上一次生成ID的时间戳，说明系统时钟回退，抛出异常
        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("系统时钟回退，无法生成ID");
        }

        // 如果是同一时间戳，则递增序列号
        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 如果序列号达到最大值，则等待下一毫秒
            if (sequence == 0) {
                currentTimestamp = getNextTimestamp();
            }
        } else {
            // 不同时间戳，序列号重置为0
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        // 组合各部分生成ID
        return (currentTimestamp - START_TIMESTAMP) << TIMESTAMP_LEFT
                | dataCenterId << DATA_CENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }
    // 获取当前时间戳
    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    // 获取下一毫秒的时间戳
    private long getNextTimestamp() {
        long timestamp = getCurrentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTimestamp();
        }
        return timestamp;
    }
}
