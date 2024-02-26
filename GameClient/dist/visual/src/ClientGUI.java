import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientGUI extends JFrame implements ActionListener, WindowListener {
  private JLabel ipaddressLabel;
  private JLabel portLabel;
  private JTextField ipaddressText;
  private JTextField portText;

  private JButton registerButton;

  private JPanel registerPanel;
  public static JPanel gameStatusPanel;
  private Client client;
  private Tank clieTank;

  private static int score;

  int width = 800, height = 600;
  boolean isRunning = true;
  private GameBoardPanel boardPanel;

  private SoundManager soundManager;

  public ClientGUI() {
    score = 0;
    setTitle("Tank Game");
    setSize(width, height);
    setLocation(100, 100);
    getContentPane().setLayout(new BorderLayout());

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addWindowListener(this);
    registerPanel = new JPanel();
    registerPanel.setLayout(null);
    registerPanel.setSize(200, 200);
    registerPanel.setBounds(0, 0, 200, 200);
    registerPanel.setBackground(Color.LIGHT_GRAY);

    gameStatusPanel = new JPanel();
    gameStatusPanel.setLayout(null);
    gameStatusPanel.setSize(200, 200);
    gameStatusPanel.setBounds(0, 200, 200, 200);
    gameStatusPanel.setBackground(Color.LIGHT_GRAY);

    ipaddressLabel = new JLabel("IP Address");
    ipaddressLabel.setBounds(10, 10, 80, 25);

    portLabel = new JLabel("Port");
    portLabel.setBounds(10, 40, 80, 25);

    JLabel scoreLabel = new JLabel("Score: 0");
    scoreLabel.setBounds(10, 10, 80, 25);

    ipaddressText = new JTextField("localhost");
    ipaddressText.setBounds(100, 10, 80, 25);

    portText = new JTextField("4444");
    portText.setBounds(100, 40, 80, 25);

    registerButton = new JButton("Register");
    registerButton.setBounds(10, 80, 80, 25);
    registerButton.addActionListener(this);
    registerButton.setFocusable(true);

    registerPanel.add(ipaddressLabel);
    registerPanel.add(portLabel);
    registerPanel.add(ipaddressText);
    registerPanel.add(portText);
    registerPanel.add(registerButton);

    gameStatusPanel.add(scoreLabel);

    client = Client.getGameClient();

    clieTank = new Tank();
    boardPanel = new GameBoardPanel(clieTank, client, isRunning);

    getContentPane().add(registerPanel, BorderLayout.WEST);
    getContentPane().add(gameStatusPanel, BorderLayout.EAST);
    getContentPane().add(boardPanel); // Removed unnecessary string argument
    setVisible(true);
  }

  public int getScore() {
    return score;
  }

  public void setScore(int scoreParameter) {
    score += scoreParameter;
    JLabel scoreLabel = new JLabel(); // Initialize scoreLabel
    scoreLabel.setText("Score: " + score);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    if (obj == registerButton) {
      // Add your code here
    }
  }

  {
    registerButton.setEnabled(false);

    try {
      // Remove the unused InnerClientRecivingThread class
      client.connectToServer(ipaddressText.getText(), Integer.parseInt(portText.getText()), clieTank.getXPoisition(),
          clieTank.getXPoisition());
      soundManager = new SoundManager();
      boardPanel.setGameStatus(true);
      boardPanel.setFocusable(true);

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }

      new ClientRecivingThread(client.getSocket()).start();
      registerButton.setFocusable(false);
      boardPanel.setFocusable(true);
    } catch (Exception e1) {
      JOptionPane.showMessageDialog(null, "Server is not available");
      System.out.println("Server is not available");
      registerButton.setEnabled(true);
    }
  }

  public void windowOpened(WindowEvent e) {
  }

  public void windowClosing(WindowEvent e) {
    Client.getGameClient().sendToServer(new Protocol().ExitMessagePacket((int) clientTankID()));
  }

  private Object clientTankID() {
    return null;
  }

  public void windowClosed(WindowEvent e) {
  }

  public void windowIconified(WindowEvent e) {
  }

  public void windowDeiconified(WindowEvent e) {
  }

  public void windowActivated(WindowEvent e) {
  }

  public void windowDeactivated(WindowEvent e) {
  }

  public class ClientRecivingThread extends Thread {
    Socket clientSocket;
    DataInputStream reader;

    public ClientRecivingThread(Socket clientSocket) {
      this.clientSocket = clientSocket;
      try {
        reader = new DataInputStream(clientSocket.getInputStream());
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    public void run() {
      while (isRunning) {
        try {
          String message = reader.readUTF();
          if (message.startsWith("register")) {
            String[] messageParts = message.split(" ");
            int id = Integer.parseInt(messageParts[1]);
            clieTank.setTankID(id);
            boardPanel.regissterNewTank(clieTank);
          } else if (message.startsWith("move")) {
            String[] messageParts = message.split(" ");
            int id = Integer.parseInt(messageParts[1]);
            int x = Integer.parseInt(messageParts[2]);
            int y = Integer.parseInt(messageParts[3]);
            int direction = Integer.parseInt(messageParts[4]);
            Tank tank = boardPanel.getTank(id);
            if (tank != null) {
              tank.setXPosition(x);
              tank.setYPosition(y);
              tank.setDirection(direction);
            }
          } else if (message.startsWith("remove")) {
            String[] messageParts = message.split(" ");
            int id = Integer.parseInt(messageParts[1]);
            boardPanel.removeTank(id);
          } else if (message.startsWith("bomb")) {
            String[] messageParts = message.split(" ");
            int id = Integer.parseInt(messageParts[1]);
            Tank tank = boardPanel.getTank(id);
            if (tank != null) {
              tank.getBomb();
            }
          } else if (message.startsWith("score")) {
            String[] messageParts = message.split(" ");
            int id = Integer.parseInt(messageParts[1]);
            int score = Integer.parseInt(messageParts[2]);
            Tank tank = boardPanel.getTank(id);
            if (tank != null) {
              tank.setHealth(score);
            }
          } else if (message.startsWith("gameover")) {
            boardPanel.setGameStatus(false);
            soundManager.playGameOverSound();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
