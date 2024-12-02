package com.javawork.hunDouLuo.ui;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;


import java.util.concurrent.CountDownLatch;




public class LoginSoundThread extends Thread {
    private MediaPlayer mediaPlayer;
    public boolean playSound = true;
    private final CountDownLatch latch = new CountDownLatch(1);
    public boolean isplaying = false;

    @Override
    public void run() {
        try {
            playLoginSound();
            isplaying = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 等待mediaPlayer初始化完成
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void playLoginSound() {
        try {
            String soundPath = "audios/login.wav"; // 替换为音频文件路径
            File audioFile = new File(soundPath);
            if (!audioFile.exists()) {
                System.out.println("音频文件不存在：" + soundPath);
                return;
            }

            Media media = new Media(audioFile.toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            // 设置播放完成后的回调
            mediaPlayer.setOnEndOfMedia(() -> {
                stopLoginSound();
                System.out.println("音效播放完成！");
            });

            // 播放音效
            mediaPlayer.play();

            // 通知mediaPlayer已经初始化完成
            latch.countDown();

            System.out.println("音效播放中：" + soundPath);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("音效播放失败！");
        }
    }

    public void stopLoginSound() {
        if (mediaPlayer != null) {
            System.out.println("尝试停止音效...");
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop();
                System.out.println("音效已停止");
            }
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        playSound = false;
    }
}