package com.javawork.hunDouLuo.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class victoryframe extends JFrame{
    private int x;
    private int y;
    private boolean running=true;
    private MainFrame mainFrame;
    public victoryframe(int x,int y,MainFrame frame) {
        setTitle("Custom Frame");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);        setLocationRelativeTo(null); // Center the frame
        setUndecorated(true); // Remove window decorations
        Point mainFrameLocation = new Point(x,y);
        int vx = mainFrameLocation.x + 700;
        int vy = mainFrameLocation.y + 350;
        setLocation(vx, vy);
        // Create buttons
        JButton button1 = createButton("Exit(click here)");
//        JButton button2 = createButton("Back");
        try {
            // 构建字体文件路径：与src平级的font目录下
            File fontFile = new File("font\\ThaleahFat.ttf");
            // 加载字体文件
            if(!fontFile.exists()){
                System.err.println("字体文件不存在: ");
            }
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            // 设置字体大小并应用到Graphics2D
            Font scaledFont = customFont.deriveFont(40f);
            button1.setFont(scaledFont);
//            button2.setFont(scaledFont);
        } catch (FontFormatException e) {
            System.err.println("字体文件格式错误: " + e.getMessage());
        } catch (IOException e){
            System.err.println("加载字体文件失败: " + e.getMessage());
        }

        button1.setForeground(Color.YELLOW);
//        button2.setForeground(Color.YELLOW);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                  frame.dispose();
                  frame.running=false;
                  running=false;
                  frame.victorySoundThread.playSound=false;
                  dispose();
//                System.exit(0);
            }
        });
//        button2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//               System.out.println("Back");
//            }
//        });
        // Add buttons to the frame
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);
        panel.setLayout(new FlowLayout());
        panel.add(button1);
//        panel.add(button2);
        add(panel);
    }
    public void run(){
       while(running){}
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }
};
