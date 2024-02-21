import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {
 private final int LEFT = 37;
 private final int RIGHT = 39;
 private final int UP = 38;
 private final int DOWN = 40;
 private static int status = 0;

 private Tank tank;
 private Client client;

 public InputManager(Tank tank) {
  this.client = Client.getGameClient();
  this.tank = tank;
 }

 public void keyTyped(KeyEvent e) {
 }

 @Override
 public void keyPressed(KeyEvent e) {
  if (e.getKeyCode() == LEFT) {
   if (tank.getDirection() == 1 | tank.getDirection() == 3) {
    tank.moveLeft();

    client.sendToServer(new Protocol().UpdatePacket(tank.getXPosition(), tank.getYPosition(), tank.getDirection(), tank.getTankID()));
   }
   else if (tank.getDirection() == 4) {
    tank.moveRight();

    client.sendToServer(new Protocol().UpdatePacket(tank.getXPosition(), tank.getYPosition(), tank.getDirection(), tank.getTankID()));
   }
   else if (e.getKeyCode() == RIGHT) {
    if (tank.getDirection() == 1 | tank.getDirection() == 3) {
     tank.moveRight();

     client.sendToServer(new Protocol().UpdatePacket(tank.getXPosition(), tank.getYPosition(), tank.getDirection(), tank.getTankID()));
    }
    else if (tank.getDirection() == 2) {
     tank.moveLeft();

     client.sendToServer(new Protocol().UpdatePacket(tank.getXPosition(), tank.getYPosition(), tank.getDirection(), tank.getTankID()));
    }
   }
   else if (e.getKeyCode() == UP) {
    if (tank.getDirection() == 2 | tank.getDirection() == 4) {
     tank.moveUp();

     client.sendToServer(new Protocol().UpdatePacket(tank.getXPosition(), tank.getYPosition(), tank.getDirection(), tank.getTankID()));
    }
    else if (tank.getDirection() == 1) {
     tank.moveDown();

     client.sendToServer(new Protocol().UpdatePacket(tank.getXPosition(), tank.getYPosition(), tank.getDirection(), tank.getTankID()));
    }
   }
   else if (e.getKeyCode() == DOWN) {
    if (tank.getDirection() == 1 | tank.getDirection() == 3) {
     tank.moveDown();

     client.sendToServer(new Protocol().UpdatePacket(tank.getXPosition(), tank.getYPosition(), tank.getDirection(), tank.getTankID()));
    }
    else if (e.getKeyCode() == UP) {
     if (tank.getDirection() == 2 | tank.getDirection() == 4) {
      tank.moveDown();

      client.sendToServer(new Protocol().UpdatePacket(tank.getXPosition(), tank.getYPosition(), tank.getDirection(), tank.getTankID()));
     }
     else if (tank.getDirection() == 1) {
      tank.moveUp();

      client.sendToServer(new Protocol().UpdatePacket(tank.getXPosition(), tank.getYPosition(), tank.getDirection(), tank.getTankID()));
     }
    }
     else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      client.sendToServer(new Protocol().ShotPacket(tank.getTankID()));
      tank.shot();
     }

 public void keyReleased(KeyEvent e) {
  if (e.getKeyCode() == LEFT) {
   status = 0;
  } else if (e.getKeyCode() == RIGHT) {
   status = 0;
  } else if (e.getKeyCode() == UP) {
   status = 0;
  } else if (e.getKeyCode() == DOWN) {
   status = 0;
  }
 }
}