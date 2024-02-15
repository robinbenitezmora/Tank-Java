import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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

 public static int getScore() {
  return score;
 }

 public static void setScore(int scoreParameter) {
  score += scoreParameter;
  AbstractButton scoreLabel;
  scoreLabel.setText("Score: " + score);
 }

 @Override
  public void actionPerformed(ActionEvent e) {
   Object obj = e.getSource();
   // Add your code here
  }if(obj==registerButton)

 {
  registerButton.setEnabled(false);

  try {
   client.connectToServer(ipaddressText.getText(), Integer.parseInt(portText.getText()), clieTank.getXPosition(),
     clieTank.getYPosition());
   soundManager = new SoundManager();
   boardPanel.setGameStatus(true);
   boderPanel.repaint();

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
 }

 @Override
 public void windowOpened(WindowEvent e) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'windowOpened'");
 }

 @Override
 public void windowClosing(WindowEvent e) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'windowClosing'");
 }

 @Override
 public void windowClosed(WindowEvent e) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'windowClosed'");
 }

 @Override
 public void windowIconified(WindowEvent e) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'windowIconified'");
 }

 @Override
 public void windowDeiconified(WindowEvent e) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'windowDeiconified'");
 }

 @Override
 public void windowActivated(WindowEvent e) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'windowActivated'");
 }

 @Override
 public void windowDeactivated(WindowEvent e) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'windowDeactivated'");
 }

 @Override
 public void actionPerformed(ActionEvent e) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
 }
