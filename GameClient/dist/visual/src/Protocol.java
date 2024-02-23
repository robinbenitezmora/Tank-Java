
public class Protocol {
 private String message = "";

 public Protocol() {
 }

 public String RegisterPacket(int x, int y) {
  message = "REGISTER " + x + " " + y;
  return message;
 }

 public String RemoveClientPacket(int id) {
  message = "REMOVE " + id;
  return message;
 }

 public String ExitMessagePacket(int id) {
  message = "EXIT " + id;
  return message;
 }

 public String UpdatePacket(int x, int y, int id, int dir) {
  message = "UPDATE " + x + " " + y + " " + id + " " + dir;
  return message;
 }

 public String MessagePacket(String message2) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'MessagePacket'");
 }

 public String ExitPacket() {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'ExitPacket'");
 }

 public String UpdatePacket(int xPosition, int yPosition, int direction, Object tankID) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'UpdatePacket'");
 }

 public String ShotPacket(Object tankID) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'ShotPacket'");
 }
}
