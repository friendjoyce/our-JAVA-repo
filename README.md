<center><h2>魂动罗小游戏</h2></center>
yjx,zy,xrh,yf
## 1. Demo简介

这里最后可以写我们的项目介绍，或者有关项目的技术点，或者项目使用的工具。有什么比较重点的知识点也可以写
本demo仿制了一个魂斗罗小游戏，纯JavaSE实现。
### 1.1 技术应用
* 键盘控制
* 图片显示
* 音频播放
* 多线程控制
## 2. demo使用
```java

    //main做了更新，在这里临时展示出来（因为源代码忘了
   public static void main(String[] args) {
    //因为宿舍校园网不能直接通过jdbc连接云端mysql，所以需要配置ssh连接
    SSHService ssh = new SSHService();
    ssh.sshRun();
    //fx的设置，如果没有fx窗口显示 fx仍然不会停止
    Platform.setImplicitExit(false);
    //记录当前玩家的username
    username = App.gameStartfx();
    //启动主游戏
    Thread game = new Thread(new MainFrame());
    game.start();
    try {
        game.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    
    //生命耗尽，游戏结束调用
    try {
        App.gameEnd(username,score);
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
    }
    return;
}
 
//按照右侧使用说明控制，

```
~~无限生命。~~ 测试需要，暂时只有一条命，（修改heronum=1）

## 3. 总结

## 4.一些资源的使用：
    ##### 1. 字体：https://www.dafont.com/championship.font