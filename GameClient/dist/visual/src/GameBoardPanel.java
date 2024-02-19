import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameBoardPanel extends JPanel {

 private Tank tank;
 private int width = 800;
 private int height = 600;
 private static ArrayList<Tank> tanks;
 private boolean gameStatus;

 public GameBoardPanel(Tank tank, Client client, boolean gameStatus) {
  this.tank = tank;
  this.gameStatus = gameStatus;
  setSize(width, height);
  setBounds(0, 0, width, height);
  addKeyListener((java.awt.event.KeyListener) new InputManager());
  setFocusable(true);

  for (int i = 0; i < 100; i++) {
   tanks.add(null);
  }
 }

 public void paintComponent(int gr) {

 /**
  * @param gr
  */
 public void paintComponent(Graphics gr) {
  Graphics2D g = (Graphics2D) gr.create();

  g.setColor(Color.BLACK);
  g.fillRect(0, 0, getWidth(), getHeight());

  g.setColor(Color.WHITE);
  g.fillRect(70, 50, getWidth() - 140, getHeight() - 100);
  g.drawImage(new ImageIcon("src/images/Background.png").getImage(), 70, 50, null);
  g.setColor(Color.BLACK);
  g.setFont(new Font("Arial", Font.BOLD, 20));
  g.drawString("Game Board", 350, 30);
  g.dispose();

  if (gameStatus) {
   g.drawImage(tank.getBufferedImage(), tank.getXPosition(), tank.getYPosition(), this);
   for (int j = 0; j < 1000; j++) {
    if (tank.getBomb()[j] != null) {
     if (tank.getBomb()[j].stop == false) {
      g.drawImage(tank.getBomb()[j].getBufferedImg(), tank.getBomb()[j].getPosiX(), tank.getBomb()[j].getPosiY(), this);
     }
    }
   }
   for (int i; i < tanks.size(); i++) {
    if (tanks.get(i) != null) {
     g.drawImage(tanks.get(i).getBufferedImage(), tanks.get(i).getXPosition(), tanks.get(i).getYPosition(), this);

     for (int j = 0; j < 1000; j++) {
      if (tanks.get(i) != null) {
       if (tanks.get(i).getBomb()[j].stop == false) {
        g.drawImage(tanks.get(i).getBomb()[j].getBufferedImg(), tanks.get(i).getBomb()[j].getPosiX(),
          tanks.get(i).getBomb()[j].getPosiY(), this);
       }
      }
     }
    }
   }
  }

  repaint();
 }

 Graphics2D g = (Graphics2D) gr.create();

 g.setColor(Color.BLACK);g.fillRect(0,0,

 getWidth(), getHeight());

   g.setColor(Color.WHITE);
   g.fillRect(70, 50, getWidth() - 140, getHeight() - 100);
   g.drawImage(new ImageIcon("src/images/Background.png").getImage(), 70, 50, null);
   g.setColor(Color.BLACK);
   g.setFont(new Font("Arial", Font.BOLD, 20));
   g.drawString("Game Board", 350, 30);
   g.dispose();
  }if(gameStatus)

 {
   g.drawImage(tank.getBufferedImage(), tank.getXPosition(), tank.getYPosition(), this);
   for (int j = 0; j < 1000; j++) {
    if (tank.getBomb()[j] != null) {
     if (tank.getBomb()[j].stop == false) {
    g.drawImage(tank.getBomb()[j].getBufferedImg(), tank.getBomb()[j].getPosiX(), tank.getBomb()[j].getPosiY(), this);
     }
    }
   }
    for (int i; i < tanks.size(); i++) {
     if (tanks.get(i) != null) {
      g.drawImage(tanks.get(i).getBufferedImage(), tanks.get(i).getXPosition(), tanks.get(i).getYPosition(), this);

      for (int j = 0; j < 1000; j++) {
     if (tanks.get(i) != null) {
      if (tanks.get(i).getBomb()[j].stop == false) {
       g.drawImage(tanks.get(i).getBomb()[j].getBufferedImg(), tanks.get(i).getBomb()[j].getPosiX(), tanks.get(i).getBomb()[j].getPosiY(), this);
      }
     }
    }
   }
  }
 }

 repaint();

}

 public static ArrayList<Tank> getClients() {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'getClients'");
 }

 public void setGameStatus(boolean b) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'setGameStatus'");
 }

 public void setFocusable(boolean b) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'setFocusable'");
 }

 public void registerNewTank(Tank tank) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'registerNewTank'");
 }

 public Object getTank(int id) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'getTank'");
 }

 public void removeTank(int id) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'removeTank'");
 }

}
