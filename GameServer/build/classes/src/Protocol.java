public class Protocol {
 private String message = "";

 public Protocol() {
 }

 public String IDPacket(int id) {
  message = "ID" + id;
  return message;
 }

 public String NewClientPacket(int x, int y, int dir, int id) {
  message = "NEW" + x + "," + y + "," + dir + "," + id;
  return message;
 }
}
