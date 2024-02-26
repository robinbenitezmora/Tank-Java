import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GameBoardPanel extends JPanel {

  private int width = 800;
  private int height = 600;
  private static ArrayList<Tank> tanks;
  private boolean gameStatus;
  private Tank tank;

  public GameBoardPanel(Tank tank, Client client, boolean gameStatus) throws IOException {
    this.tank = tank;
    this.gameStatus = gameStatus;
    setSize(width, height);
    setBounds(0, 0, width, height);
    addKeyListener((java.awt.event.KeyListener) new InputManager(tank));
    setFocusable(true);

    for (int i = 0; i < 100; i++) {
      tanks.add(null);
    }
  }

  public void paintComponent(java.awt.Graphics gr) {
    super.paintComponent(gr);
    java.awt.Graphics2D g = (java.awt.Graphics2D) gr;

    g.setColor(java.awt.Color.BLACK); // Fix the Color class reference
    g.fillRect(0, 0, getWidth(), getHeight());

    g.setColor(java.awt.Color.WHITE);
    g.fillRect(70, 50, getWidth() - 140, getHeight() - 100);
    // ...

    if (gameStatus) {
      g.setColor(java.awt.Color.BLACK);
      g.setFont(new java.awt.Font("TimesRoman", java.awt.Font.PLAIN, 20));
      g.drawString("Game Over", 350, 300);
    }
  }

  public void regissterNewTank(Tank newTank) {
    tanks.set((int) newTank.getTankID(), newTank);
  }

  public void removeTank(int id) {
    tanks.set(id, null);
  }

  public Tank getTank(int id) {
    return tanks.get(id);
  }

  public void setGameStatus(boolean status) {
    gameStatus = status;
  }

  public static ArrayList<Tank> getClients() {
    return tanks;
  }

}
