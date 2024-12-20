package com.javawork.hunDouLuo.ui.timer;

import java.time.Duration;
import java.time.Instant;

public class GameTimer {
    private Instant startTime;
    private Instant endTime;

    public void start() {
        startTime = Instant.now();
    }

    public void stop() {
        endTime = Instant.now();
    }

    public long getElapsedTimeInMillis() {
        return Duration.between(startTime, endTime).toMillis();
    }

    public long getElapsedTimeInSeconds() {
        return Duration.between(startTime, endTime).getSeconds();
    }

    public String getElapsedTimeFormatted() {
        long totalSeconds = getElapsedTimeInSeconds();
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }


}