package com.javawork.hunDouLuo.ui;

import javax.sound.sampled.Clip;

public class SoundUtils {
    public static void playSound(String filePath) {
        try {
            Clip clip = Audios.getAudioPlayer(filePath);
            // 播放音效
            clip.start();
            // 添加一个监听器，在播放结束后释放资源
            clip.addLineListener(event -> {
                if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                    clip.close();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("音效播放失败：" + filePath);
        }
    }
}

