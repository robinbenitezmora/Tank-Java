import java.util.ArrayList;

import javax.swing.ImageIcon;

import java.applet.AudioClip;
import java.awt.image.BufferedImage;

public class Bomb {
 private java.awt.Image bombImg;
 private BufferedImage bombBuffImage;

 private int xPos;
 private int yPos;
 private int direction;
 public boolean stop = false;
 private float velocityX = 0.05f, velocityY = 0.05f;

 public Bomb(int x, int y, int direction) {
  AudioClip sound_boom = java.applet.Applet.newAudioClip(getClass().getResource("sounds/bomb.wav"));
  xPos = x;
  yPos = y;
  this.direction = direction;
  stop = false;
  bombImg = new ImageIcon("images/bomb.png").getImage();
  bombBuffImage = new BufferedImage(bombImg.getWidth(null), bombImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);
  bombBuffImage.getGraphics().drawImage(bombImg, 0, 0, null);
  Thread t = new Thread(new Runnable() {
   public void run() {
    sound_boom.play();
   }
  });
  t.start();
 }

 public int getPosiX() {
  return xPos;
 }

 public int getPosiY() {
  return yPos;
 }

 public void setPosiY(int y) {
  yPos = y;
 }

 public void setPosiX(int x) {
  xPos = x;
 }

 public BufferedImage getBombBufferedImage() {
  return bombBuffImage;
 }

 public BufferedImage getBombBuffImage() {
  return bombBuffImage;
 }

 public boolean checkCollision() {
  ArrayList<Tank> clientTanks = GameBoardPanel.getClients();
  int x, y = 0; // Initialize variable y
  for (int i = 0; i < clientTanks.size(); i++) {
   if (clientTanks.get(i) != null) {
    x = clientTanks.get(i).getXPosition();
    // ...

    if (yPos >= y && yPos <= y + 30 && xPos >= x && xPos <= x + 30) {
     clientTanks.get(i).setHealth(clientTanks.get(i).getHealth() - 1);
     return true;

    }
   }
  }
  return false;
 }

 public void startBombThread(boolean checkCollision) {
  new BombShotThread(checkCollision).start();
 }

 private class BombShotThread extends Thread {
  boolean checkCollision;

  public BombShotThread(boolean checkCollision) {
   this.checkCollision = checkCollision;
  }

  public void run() {
   if (checkCollision) {
    if (direction == 1) {
     xPos = 17 + xPos;
     while (yPos > 50) {
      yPos = (int) (yPos - yPos * velocityY);
      if (checkCollision()) {
       break;
      }

      try {
       Thread.sleep(100);
      } catch (InterruptedException e) {
       e.printStackTrace();
      }
     }
    }

    else if (direction == 2) {
     yPos = 17 + yPos;
     xPos += 30;
     while (xPos < 500) {
      xPos = (int) (xPos + xPos * velocityX);
      if (checkCollision()) {
       break;
      }

      try {
       Thread.sleep(100);
      } catch (InterruptedException e) {
       e.printStackTrace();
      }
     }
    }

    else if (direction == 3) {
     yPos += 30;
     xPos = 17 + xPos;
     while (yPos < 500) {
      yPos = (int) (yPos + yPos * velocityY);
      if (checkCollision()) {
       break;
      }

      try {
       Thread.sleep(100);
      } catch (InterruptedException e) {
       e.printStackTrace();
      }
     }
    }

    else if (direction == 4) {
     xPos = 17 + xPos;
     while (xPos > 50) {
      xPos = (int) (xPos - xPos * velocityX);
      if (checkCollision()) {
       break;
      }

      try {
       Thread.sleep(100);
      } catch (InterruptedException e) {
       e.printStackTrace();
      }
     }
    }

    stop = true;
   } else {
    if (direction == 1) {
     xPos = 17 + xPos;
     while (yPos > 50) {
      yPos = (int) (yPos - yPos * velocityY);
      try {
       Thread.sleep(100);
      } catch (InterruptedException e) {
       e.printStackTrace();
      }
     }
    }

    else if (direction == 2) {
     yPos = 17 + yPos;
     xPos += 30;
     while (xPos < 500) {
      xPos = (int) (xPos + xPos * velocityX);
      try {
       Thread.sleep(100);
      } catch (InterruptedException e) {
       e.printStackTrace();
      }
     }
    }

    else if (direction == 3) {
     yPos += 30;
     xPos = 17 + xPos;
     while (yPos < 500) {
      yPos = (int) (yPos + yPos * velocityY);
      try {
       Thread.sleep(100);
      } catch (InterruptedException e) {
       e.printStackTrace();
      }
     }
    }

    else if (direction == 4) {
     xPos = 17 + xPos;
     while (xPos > 50) {
      xPos = (int) (xPos - xPos * velocityX);
      try {
       Thread.sleep(100);
      } catch (InterruptedException e) {
       e.printStackTrace();
      }
     }
    }

    stop = true;
   }
  }
 }
}
