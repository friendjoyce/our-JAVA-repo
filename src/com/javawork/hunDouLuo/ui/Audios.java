package com.javawork.hunDouLuo.ui;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audios {

    public static Clip getAudioPlayer(String filePath) throws Exception {
        File audioFile = new File(filePath);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        return clip;
    }
}
