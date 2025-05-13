package com.chat.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdWorker {

    // 起始时间戳（2021-01-01 00:00:00），后面生成ID时用当前时间减去这个起点时间
    private final long twepoch = 1609459200000L; // 例：当前时间2025-05-16 12:00:00的毫秒数减去这个基准时间差

    // 机器编号占用的位数（5位），表示最多支持32台机器（0~31）
    private final long workerIdBits = 5L;
    // 数据中心编号占用的位数（5位），最多支持32个数据中心（0~31）
    private final long datacenterIdBits = 5L;
    // 序列号占用的位数（12位），同一毫秒最多生成4096个ID（0~4095）
    private final long sequenceBits = 12L;

    // 机器编号最大值，计算方法是2^5 - 1 = 31
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 数据中心编号最大值，同样是31
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    // 机器编号左移12位（序列号的位数）
    private final long workerIdShift = sequenceBits;
    // 数据中心编号左移(12 + 5)位
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    // 时间戳左移(12 + 5 + 5)位
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    // 序列号掩码，保证sequence不会超过4095
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;       // 机器编号（0~31）
    private long datacenterId;   // 数据中心编号（0~31）
    private long sequence = 0L;  // 序列号，表示同一毫秒内第几个ID
    private long lastTimestamp = -1L; // 上一次生成ID的时间戳

    /**
     * 下面构造方法使用了默认参数
     * @param workerId
     * @param datacenterId
     */
    public SnowflakeIdWorker(@Value("4") long workerId, @Value("5") long datacenterId) {
        // 检查机器编号是否超过最大值
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("机器ID不能超过%d且不能小于0", maxWorkerId));
        }
        // 检查数据中心编号是否超过最大值
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("数据中心ID不能超过%d且不能小于0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    // 生成下一个ID，线程安全
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上次生成ID时间，说明系统时钟回退了，抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("系统时钟异常，拒绝生成ID，当前时间 %d 早于上次生成时间 %d", timestamp, lastTimestamp));
        }

        if (lastTimestamp == timestamp) {
            // 如果和上次生成时间在同一毫秒内，序列号加1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 序列号用完了，等待下一毫秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳变化了，序列号重新置0
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        // 拼接成64位ID，格式如下：
        // 时间戳部分 | 数据中心编号 | 机器编号 | 序列号
        // 例：当前时间戳差：123456789，左移22位
        // 数据中心ID：3，左移17位
        // 机器ID：5，左移12位
        // 序列号：12
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    // 等待下一毫秒，保证时间单调递增
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    // 获取当前系统时间（毫秒）
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}

