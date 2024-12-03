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
            clip.loop(Clip.LOOP_CONTINUOUSLY); // 设置为无限循环
            while (playSound) {
                Thread.sleep(200L); // 每隔200ms检查playSound标志
            }
            clip.stop();
            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
