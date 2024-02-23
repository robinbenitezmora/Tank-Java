import java.io.ByteArrayInputStream;
import java.io.IOException;

public class LoopingByteInputStream extends ByteArrayInputStream {
 private boolean closed;

 public LoopingByteInputStream(Object object) {
  super((byte[]) object);
  closed = false;
 }

 public int read(byte[] buffer, int offset, int length) {
  if (closed) {
   return -1;
  }
  int totalBytesRead = 0;
  while (totalBytesRead < length) {
   int numBytesRead = super.read(buffer, offset + totalBytesRead, length - totalBytesRead);
   if (numBytesRead > 0) {
    totalBytesRead += numBytesRead;
   } else {
    reset();
   }
  }
  return totalBytesRead;
 }

 public void close() throws IOException {
  super.close();
  closed = true;
 }
}
