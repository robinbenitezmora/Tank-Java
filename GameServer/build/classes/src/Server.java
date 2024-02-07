import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server extends Thread {
 private ArrayList<ClientInfo> clients;
 private ServerSocket serverSocket;
 private int serverPort = 12345;

 private DataInputStream reader;
 private DataOutputStream writer;

 private Protocol protocol;
 private boolean running = true;

 public Server() throws SocketException {
  clients = new ArrayList<ClientInfo>();
  protocol = new Protocol();
  try {
   serverSocket = new ServerSocket(serverPort);
  } catch (IOException e) {
   e.printStackTrace();
  }
 }

 public void run() {
  Socket clientSocket = null;
  while (running) {
   try {
    clientSocket = serverSocket.accept();
   } catch (IOException e) {
    e.printStackTrace();
   }
   String sentence = "";
   try {
    reader = new DataInputStream(clientSocket.getInputStream());
   } catch (IOException e) {
    e.printStackTrace();
   }
   try {
    sentence = reader.readUTF();
   } catch (IOException e) {
    e.printStackTrace();
   }
   System.out.println("Received: " + sentence);
   if (sentence.startsWith("Hello")) {
    int pos = sentence.indexOf(" ");
    int x = Integer.parseInt(sentence.substring(pos + 1, sentence.indexOf(",")));
    int y = Integer
      .parseInt(sentence.substring(sentence.indexOf(",") + 1, sentence.indexOf(" ", sentence.indexOf(",") + 1)));

    try {
     writer = new DataOutputStream(clientSocket.getOutputStream());
    } catch (IOException e) {
     e.printStackTrace();
    }
    sendToClient(protocol.IDPacket(clients.size() + 1));
    try {
     BroadCastMessage(protocol.NewClientPacket(x, y, 0, clients.size() + 1));
     sendAllClients(writer);
    } catch (IOException e) {
     e.printStackTrace();
    }

    clients.add(new ClientInfo(clientSocket, x, y, 0, clients.size() + 1));
   }

   else if (sentence.startsWith("Update")) {
    int pos1 = sentence.indexOf(" ");
    int pos2 = sentence.indexOf(" ", pos1 + 1);
    int pos3 = sentence.indexOf(" ", pos2 + 1);
    int x = Integer.parseInt(sentence.substring(pos1 + 1, sentence.indexOf(",", pos1 + 1)));
    int y = Integer.parseInt(sentence.substring(sentence.indexOf(",", pos1 + 1) + 1, sentence.indexOf(" ", pos2 + 1)));
    int dir = Integer.parseInt(sentence.substring(pos3 + 1, sentence.length()));
    int id = Integer.parseInt(sentence.substring(sentence.indexOf(" ", pos2 + 1) + 1, pos3));

    if (clients.get(id - 1).setPosX(x))
     ;
    clients.get(id - 1).setPosY(y);
    clients.get(id - 1).setDir(dir);
    try {
     BroadCastMessage(sentence);
    } catch (IOException e) {
     e.printStackTrace();
    }
   }
  }

  String sentence;
  if (sentence.startsWith("Shot")) {
   try {
    BroadCastMessage(sentence);
   } catch (IOException e) {
    e.printStackTrace();
   }
  }

  else if (sentence.startsWith("Remove")) {
   int id = Integer.parseInt(sentence.substring(sentence.indexOf(" ") + 1, sentence.length()));
   clients.remove(id - 1);
   try {
    BroadCastMessage(sentence);
   } catch (IOException e) {
    e.printStackTrace();
   }
   ArrayList<Server.ClientInfo> cients;
   cients.set(id - 1, null);
  }

  else if (sentence.startsWith("Exit")) {
   int id = Integer.parseInt(sentence.substring(sentence.indexOf(" ") + 1, sentence.length()));

   try {
    BroadCastMessage(sentence);
   } catch (IOException e) {
    e.printStackTrace();
   }
   if (clients.get(id - 1) != null) {
    clients.remove(id - 1);
    clients.set(id - 1, null);
   }
  }

  try {
   reader.close();
   writer.close();
   clientSocket.close();
   clientSocket.close();
  } catch (IOException e) {
   e.printStackTrace();
  }
 }

 public void stopServer() throws IOException {
  running = false;
 }

 public void BroadCastMessage(String message) throws IOException {
  for (int i = 0; i < clients.size(); i++) {
   writer = new DataOutputStream(clients.get(i).getSocket().getOutputStream());
   writer.writeUTF(message);
  }
 }

 public void sendToClient(String message) {
  if (message.equals("exit"))
   System.exit(0);
  else {
   try {
    writer.writeUTF(message);
   } catch (IOException e) {
    e.printStackTrace();
   }
  }
 }

 public void sendAllClients(DataOutputStream writer) {
  int x, y, dir;
  for (int i = 0; i < clients.size(); i++) {
   if (clients.get(i) != null) {
    x = clients.get(i).getPosX();
    y = clients.get(i).getPosY();
    dir = clients.get(i).getDir();
    try {
     writer.writeUTF(protocol.NewClientPacket(x, y, dir, i + 1));
    } catch (IOException e) {
     e.printStackTrace();
    }
   }
  }
 }

public class ClientInfo {
 DataOutputStream writer;
 int posX, posY, dir;

 public ClientInfo(DataOutputStream writer, int posX, int posY, int dir) {
  this.writer = writer;
  this.posX = posX;
  this.posY = posY;
  this.dir = dir;
 }

 public DataOutputStream getWriter() {
  return writer;
 }

 public int getPosX() {
  return posX;
 }

 public int getPosY() {
  return posY;
 }

 public int getDir() {
  return dir;
 }


 public void setWriter(DataOutputStream writer) {
  this.writer = writer;
 }

 public void setPosX(int posX) {
  this.posX = posX;
 }

 public void setPosY(int posY) {
  this.posY = posY;
 }
}
}```