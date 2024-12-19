 package com.javawork.hunDouLuo.ui;
 
 import java.awt.Graphics2D;
 
 abstract class Bullet
 {//子弹的当前位置
   public FloatPoint position;
   //表示子弹是否存活，用于判断是否继续绘制和更新
   public boolean isAlive = true;
   //子弹的半径大小，用于绘制和碰撞检测。
   public float size;
   //子弹的能量值，用于计算命中效果或销毁条件。
   public int energy;
 
   public Bullet(FloatPoint position, boolean isAlive, int energy)
   {
     this.position = position;
     this.isAlive = isAlive;
     this.energy = energy;
   }
 
   public abstract void drawBubblet(Graphics2D paramGraphics2D, float paramFloat);
 }

