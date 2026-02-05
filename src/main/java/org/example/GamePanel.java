package org.example;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //screen settings
    final int originalTileSize = 16; //16x16 Map tile
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12; //Screen size 16x12 (4:3 Ratio)
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels


    //FPS
    int fps = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    //Set players default position

    int playerX = 100;
    int platerY = 100;
    int playerSpeed = 4;

    public GamePanel()  {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {

        double drawInterval = 1000000000/fps;// 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){





            // 1 UPDATE: We use this for updating information, such as character position
            update();
            // 2 DRAW: Draw the screen with the updated information
            repaint();



            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if (remainingTime<0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void update(){

        if(keyH.upPressed){
            platerY -= playerSpeed;
        }
        else if(keyH.downPressed){
            platerY += playerSpeed;
        }
        else if(keyH.rightPressed){
            playerX += playerSpeed;
        }
        else if(keyH.leftPressed){
            playerX -= playerSpeed;
        }
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics g2 = (Graphics2D)g;

        g2.setColor(Color.WHITE);

        g2.fillRect(playerX, platerY, tileSize, tileSize);

        g2.dispose();
    }

}
