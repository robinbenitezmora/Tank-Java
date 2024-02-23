import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class InputManager implements KeyListener {
    private final int LEFT = 37;
    private final int RIGHT = 39;
    private final int UP = 38;
    private final int DOWN = 40;
    private static int status = 0;

    private Tank tank;
    private Client client;

    public InputManager(Tank tank) throws IOException {
        this.client = Client.getInstance();
        this.tank = tank;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == LEFT) {
            status = 1;
            tank.setDirection(1);
            client.sendUpdate(tank.getX(), tank.getX(), tank.getDirection());
        } else if (e.getKeyCode() == RIGHT) {
            status = 2;
            tank.setDirection(2);
            client.sendUpdate(tank.getX(), tank.getX(), tank.getDirection());
        } else if (e.getKeyCode() == UP) {
            status = 3;
            tank.setDirection(3);
            client.sendUpdate(tank.getX(), tank.getX(), tank.getDirection());
        } else if (e.getKeyCode() == DOWN) {
            status = 4;
            tank.setDirection(4);
            client.sendUpdate(tank.getX(), tank.getX(), tank.getDirection());
        }
    }

    public void keyTyped(KeyEvent e) {
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

    public int getStatus() {
        return status;
    }
}
