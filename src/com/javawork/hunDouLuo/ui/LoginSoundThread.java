package com.javawork.hunDouLuo.ui;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class LoginSoundThread implements Runnable {
    private MediaPlayer mediaPlayer;
    public boolean playSound = true;
    @Override
    public void run() {
        // 确保在 JavaFX 应用线程中运行
        Platform.runLater(() -> {
            try {
                playLoginSound();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("音效播放失败！");
        }
    }

    public void stopLoginSound() {
        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop();
            }
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        playSound = false;
    }
}

