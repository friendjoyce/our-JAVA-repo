package com.javawork.hunDouLuo.ui;

import javax.sound.sampled.Clip;

public class PreBossBackgroundSoundThread implements Runnable {
    private Clip clip;
    public boolean playSound = true;

    @Override
    public void run() {
        playBackgroundSound();
    }

    private void playBackgroundSound() {
        try {
            String soundPath = "audios/stone_landing.wav"; // 替换为音频文件路径
            clip = Audios.getAudioPlayer(soundPath);

            // 播放音效
            clip.start();

            // 等待音效播放完成
            while (playSound && clip.isRunning()) {
                Thread.sleep(152000L); // 每隔 200ms 检查一次
            }

            // 停止播放
            clip.stop();
            clip.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
