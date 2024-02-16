import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.Reader;
import java.net.Socket;

public class ClientGUI extends JFrame implements ActionListener, WindowListener {
  private JLabel ipaddressLabel;
  private JLabel portLabel;
  private static JLabel scorLabel;

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
    boardPanel = new GameBoardPanel();

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
    AbstractButton scoreLabel;
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
      class ClientRecivingThread extends Thread {
        private Socket socket;

        public ClientRecivingThread(Socket socket) {
          this.socket = socket;
        }

        @Override
        public void run() {
          // Add your code here
        }
      }

      client.connectToServer(ipaddressText.getText(), Integer.parseInt(portText.getText()), clieTank.getXPosition(),
          clieTank.getYPosition());
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
    } catch (IOException e1) {
      JOptionPane.showMessageDialog(null, "Server is not available");
      System.out.println("Server is not available");
      registerButton.setEnabled(true);
    }
  }

  public void windowOpened(WindowEvent e) {
  }

  public void windowClosing(WindowEvent e) {
    Client.getGameClient().sendToServer(new Protocol().ExitMessagePacket(clientTankID()));
  }

  private Object clientTankID() {
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
    private Object clientTank;

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
        String sentence = "";
        try {
          sentence = reader.readUTF();
        } catch (IOException ex) {
          ex.printStackTrace();
        }
        if (sentence.startsWith("ID")) {
          int id = Integer.parseInt(sentence.substring(2));
          ((Tank) clientTank).setTankID(id);
          System.out.println("My ID= " + id);
        } else if (sentence.startsWith("NewClient")) {
          int pos1 = sentence.indexOf(',');
          int pos2 = sentence.indexOf('-');
          int pos3 = sentence.indexOf('|');
          int x = Integer.parseInt(sentence.substring(9, pos1));
          int y = Integer.parseInt(sentence.substring(pos1 + 1, pos2));
          int dir = Integer.parseInt(sentence.substring(pos2 + 1, pos3));
          int id = Integer.parseInt(sentence.substring(pos3 + 1, sentence.length()));
          if (z) {
            boardPanel.registerNewTank(new Tank());
          }
        } else if (sentence.startsWith("Update")) {
          int pos1 = sentence.indexOf(',');
          int pos2 = sentence.indexOf('-');
          int pos3 = sentence.indexOf('|');
          int x = Integer.parseInt(sentence.substring(6, pos1));
          int y = Integer.parseInt(sentence.substring(pos1 + 1, pos2));
          int dir = Integer.parseInt(sentence.substring(pos2 + 1, pos3));
          int id = Integer.parseInt(sentence.substring(pos3 + 1, sentence.length()));

          if (id != ((Tank) clientTank).getTankID()) {
            boardPanel.getTank(id).setXpoistion(x);
            boardPanel.getTank(id).setYposition(y);
            boardPanel.getTank(id).setDirection(dir);
            boardPanel.repaint();
          }

        } else if (sentence.startsWith("Shot")) {
          int id = Integer.parseInt(sentence.substring(4));

          if (id != clientTank.getTankID()) {
            boardPanel.getTank(id).Shot();
          }

        } else if (sentence.startsWith("Remove")) {
          int id = Integer.parseInt(sentence.substring(6));

          if (id == clientTank.getTankID()) {
            int response = JOptionPane.showConfirmDialog(null, "Sorry, You are loss. Do you want to try again ?",
                "Tanks 2D Multiplayer Game", JOptionPane.OK_CANCEL_OPTION);
            if (response == JOptionPane.OK_OPTION) {
              // client.closeAll();
              setVisible(false);
              dispose();

              new ClientGUI();
            } else {
              System.exit(0);
            }
          } else {
            boardPanel.removeTank(id);
          }
        } else if (sentence.startsWith("Exit")) {
          int id = Integer.parseInt(sentence.substring(4));

          if (id != clientTank.getTankID()) {
            boardPanel.removeTank(id);
          }
        } else if (sentence.startsWith("Score")) {
          int id = Integer.parseInt(sentence.substring(5, 6));
          int score = Integer.parseInt(sentence.substring(7));
          if (id == clientTank.getTankID()) {
            setScore(score);
          }
        } else if (sentence.startsWith("Gameover")) {
          int id = Integer.parseInt(sentence.substring(8));
          if (id == clientTank.getTankID()) {
            int response = JOptionPane.showConfirmDialog(null,
                "Congratulations, You are win. Do you want to play again ?", "Tanks 2D Multiplayer Game",
                JOptionPane.OK_CANCEL_OPTION);
            if (response == JOptionPane.OK_OPTION) {
              // client.closeAll();
              setVisible(false);
              dispose();

              new ClientGUI();
            } else {
              System.exit(0);
            }
          } else {
            int response = JOptionPane.showConfirmDialog(null, "Sorry, You are loss. Do you want to try again ?",
                "Tanks 2D Multiplayer Game", JOptionPane.OK_CANCEL_OPTION);
            if (response == JOptionPane.OK_OPTION) {
              // client.closeAll();
              setVisible(false);
              dispose();

              new ClientGUI();
            } else {
              System.exit(0);
            }
          }
        }
      }
    }
  }
}
