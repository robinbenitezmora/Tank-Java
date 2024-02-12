public class Bomb {
 private Image bombImg;
 private BufferedImage bombBuffImage;

 private int xPos;
 private int yPos;
 private int direction;
 public boolean stop = false;
 private float velocityX = 0.05f, velocityY = 0.05f;

 public Bomb(int x, int y, int direction) {
  final SimpleSoundPlayer sound_boom = new SimpleSoundPlayer("sounds/boom.wav");
  final InputStream stream_boom = new ByteArrayInputStream(sound_boom.getSamples());
  xPos = x;
  yPos = y;
  this.direction = direction;
  stop = false;
  bombImg = new ImageIcon("images/bomb.png").getImage();
  bombBuffImage = new BufferedImage(bombImg.getWidth(null), bombImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
  bombBuffImage.getGraphics().drawImage(bombImg, 0, 0, null);
  Thread t = new Thread(new Runnable) {
   public void run() {
    sound_boom.play(stream_boom);
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
  int x, y;
  for (int i = 0; i < clientTanks.size(); i++) {
   if (clientTanks.get(i) != null) {
    x = clientTanks.get(i).getXPosition();
    y = clientTanks.get(i).getYPosition();

    if (yPos >= y && yPos <= y + 30 && xPos >= x && xPos <= x + 30) {
     clientGUI.setScore(50);
     clientGUI.gameStatusPanel.repaint();

     try {
      Thread.sleep(1000);
     } catch (InterruptedException e) {
      e.printStackTrace();
     }
     if (clientTanks.get(i) != null)
      Client.getGameClient().sendToServer(new Protocol().RemoveClientPacket(clientTanks.get(i).getTankID()));
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
