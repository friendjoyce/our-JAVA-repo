package com.javawork.hunDouLuo.ui;

import javax.sound.midi.*;

public class BossBackgroundSoundThread implements Runnable {
    private Sequencer sequencer; // 用于播放 MIDI 的核心对象
    public boolean playSound = true;

    @Override
    public void run() {
        playBackgroundSound();
    }

    private void playBackgroundSound() {
        try {
            String soundPath = "audios/Bossbg.mid"; // 替换为 MIDI 文件路径
            // 加载 MIDI 文件
            Sequence sequence = MidiSystem.getSequence(new java.io.File(soundPath));
            // 获取 Sequencer（MIDI 播放器）
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(sequence);
            // 设置循环播放
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            // 播放音效
            sequencer.start();
            System.out.println("MIDI 背景音乐播放中：" + soundPath);
            // 等待播放标志变为 false
            while (playSound && sequencer.isRunning()) {
                Thread.sleep(152000L); // 每秒检查一次
            }
            // 停止播放
            sequencer.stop();
            sequencer.close();
            System.out.println("MIDI 背景音乐已停止");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("MIDI 播放失败！");
        }
    }

    public void stopBackgroundMusic() {
        if (sequencer != null && sequencer.isOpen()) {
            sequencer.stop();
            sequencer.close();
            System.out.println("MIDI 背景音乐手动停止");
        }
    }
}
