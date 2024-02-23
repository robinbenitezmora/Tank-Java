import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
  private Socket clientSocket;
  private DataInputStream reader;
  private DataOutputStream writer;
  private Protocol protocol;

  private static Client client;

  private Client() throws IOException {
    protocol = new Protocol();
  }

  public void register(String Ip, int port, int posX, int posY) throws IOException {
    clientSocket = new Socket(Ip, port);

    writer.writeUTF(protocol.RegisterPacket(posX, posY));
  }

  public void sendToServer(String message) {
    if (message.equals("exit")) {
      try {
        writer.writeUTF(protocol.ExitPacket());
        clientSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        writer.writeUTF(protocol.MessagePacket(message));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public String readFromServer() {
    try {
      return reader.readUTF();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void close() {
    try {
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Client getInstance() throws IOException {
    if (client == null) {
      client = new Client();
    }
    return client;
  }
}
