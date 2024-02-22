
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

 public String ShotPacket(int id) {
  message = "SHOT " + id;
  return message;
 }
}
