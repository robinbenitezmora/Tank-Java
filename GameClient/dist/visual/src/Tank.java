import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Tank {
 private Image[] tankImages;
 private BufferedImage bufferedImage;
 private Bomb bomb[] = new Bomb[1000];
 private int curBomb = 0;
 private int tankID;
 private int positionX = -1, positionY = -1;
 private int direction = 1;
 private float velocityX = 0.03125f, velocityY = 0.03125f;
 private int width = 560, height = 560;
 private int health = 100;

 public int getDirection() {
  return direction;
 }

 public Tank() {
  while (positionX < 70 | positionY < 50 | positionX > width - 43 | positionY > height - 43) {
   positionX = (int) (Math.random() * width);
   positionY = (int) (Math.random() * height);
  }
  loadImages(4);
 }

 public Tank(int x, int y, int dir, int id) {
  positionX = x;
  positionY = y;
  direction = dir;
  tankID = id;
  loadImages(0);
 }

 private void loadImages(int a) {
  tankImages = new Image[4];
  for (int i = a; i < tankImages.length + a; i++) {
   tankImages[i - a] = new ImageIcon("images/tank" + i + ".png").getImage();
  }
  bufferedImage = new BufferedImage();
  Graphics g = bufferedImage.getGraphics();
  g.drawImage(tankImages[0], 0, 0, null);
  g.dispose();
 }

 public BufferedImage getBufferedImage() {
  return bufferedImage;
 }

 public int getXPoisition() {
  return positionX;
 }

 public int getYPoisition() {
  return positionY;
 }

 public Bomb[] getBomb() {
  return bomb;
 }

 public void moveLeft() {
  if (direction == 1 | direction == 3) {
   bufferedImage = new BufferedImage();
   bufferedImage.createGraphics().drawImage(tankImages[2], 0, 0, null);
   direction = 2;
  } else {
   int temp;

   temp = (int) (positionX - velocityX * positionX);
   if (checkCollision(temp, positionY) == false && temp < 70) {
    positionX = 70;
   } else if (checkCollision(temp, positionY) == false) {
    positionX = temp;
   }
  }
 }

 public void moveRight() {
  if (direction == 1 | direction == 3) {
   bufferedImage = new BufferedImage();
   bufferedImage.createGraphics().drawImage(tankImages[3], 0, 0, null);
   direction = 4;
  } else {
   int temp;

   temp = (int) (positionX + velocityX * positionX);
   if (checkCollision(temp, positionY) == false && temp > width - 43) {
    positionX = width - 43;
   } else if (checkCollision(temp, positionY) == false) {
    positionX = temp;
   }
  }
 }

 public void moveUp() {
  if (direction == 2 | direction == 4) {
   bufferedImage = new BufferedImage();
   bufferedImage.createGraphics().drawImage(tankImages[1], 0, 0, null);
   direction = 1;
  } else {
   int temp;

   temp = (int) (positionY - velocityY * positionY);
   if (checkCollision(positionX, temp) == false && temp < 50) {
    positionY = 50;
   } else if (checkCollision(positionX, temp) == false) {
    positionY = temp;
   }
  }
 }

 public void moveDown() {
  if (direction == 2 | direction == 4) {
   bufferedImage = new BufferedImage();
   bufferedImage.createGraphics().drawImage(tankImages[0], 0, 0, null);
   direction = 3;
  } else {
   int temp;

   temp = (int) (positionY + velocityY * positionY);
   if (checkCollision(positionX, temp) == false && temp > height - 43) {
    positionY = height - 43;
   } else if (checkCollision(positionX, temp) == false) {
    positionY = temp;
   }
  }
 }

 public void shot() {
  bomb[curBomb] = new Bomb(this.getXPoisition(), this.getYPoisition(), direction);

  bomb[curBomb].startBombThread(true);
  curBomb++;
 }

 public Bomb[] getBombs() {
  return bomb;
 }

 public void setTankID(int id) {
  tankID = id;
 }

 public int getTankID() {
  return tankID;
 }

 public void setXPosition(int x) {
  positionX = x;
 }

 public void setYPosition(int y) {
  positionY = y;
 }

 public void setDirection(int dir) {
  bufferedImage = new BufferedImage();
  bufferedImage.createGraphics().drawImage(tankImages[dir - 1], 0, 0, null);
  direction = dir;
 }

 public void Shot() {
  bomb[curBomb] = new Bomb(this.getXPoisition(), this.getYPoisition(), direction);
  bomb[curBomb].startBombThread(true);
  curBomb++;
 }

 public boolean checkCollision(int xP, int yP) {
  ArrayList<Tank> clientTanks = GameBoardPanel.getClients();
  int x, y;
  for (int i = 0; i < clientTanks.size(); i++) {
   if (clientTanks.get(i) != null) {
    x = clientTanks.get(i).getXPoisition();
    y = clientTanks.get(i).getYPoisition();
    if (direction == 1) {
     if (xP > x - 43 && xP < x + 43 && yP > y - 43 && yP < y + 43) {
      return true;
     }
    } else if (direction == 2) {
     if (xP > x - 43 && xP < x + 43 && yP > y - 43 && yP < y + 43) {
      return true;
     }
    } else if (direction == 3) {
     if (xP > x - 43 && xP < x + 43 && yP > y - 43 && yP < y + 43) {
      return true;
     }
    } else if (direction == 4) {
     if (xP > x - 43 && xP < x + 43 && yP > y - 43 && yP < y + 43) {
      return true;
     }
    }
   }
  }
  return false;
 }
}
