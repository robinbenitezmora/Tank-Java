import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ServerGUI extends JFrame implements ActionListener {
 private JButton startServerButton;
 private JButton stopServerButton;
 private JLabel statusLabel;

 private Server server;

 public ServerGUI() {
  setTitle("Game Server");
  setBounds(350, 200, 400, 200);

  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  setLayout(null);
  startServerButton = new JButton("Start Server");
  startServerButton.setBounds(50, 50, 100, 30);
  startServerButton.addActionListener(this);

  stopServerButton = new JButton("Stop Server");
  stopServerButton.setBounds(250, 50, 100, 30);
  stopServerButton.addActionListener(this);

  statusLabel = new JLabel("Server is not running");
  statusLabel.setBounds(150, 100, 150, 30);

  getContentPane().add(statusLabel);
  getContentPane().add(startServerButton);
  getContentPane().add(stopServerButton);
  try {
   server = new Server();
  } catch (SocketException e) {
   e.printStackTrace();
  }

  setVisible(true);
 }

 public void actionPerformed(ActionEvent e) {
  if (e.getSource() == startServerButton) {
   server.start();
   startServerButton.setEnabled(false);
   statusLabel.setText("Server is running");
  }

  if (e.getSource() == stopServerButton) {
   try {
    server.stopServer();
    statusLabel.setText("Server is not running");
    try {
     Thread.sleep(1000);
    } catch (InterruptedException e1) {
     e1.printStackTrace();
    }
    System.exit(0);
   } catch (IOException e1) {
    e1.printStackTrace();
   }
  }
 }

}
