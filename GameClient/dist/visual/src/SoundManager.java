import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
 AudioFormat audioFormat;
 AudioInputStream audioInputStream;
 SourceDataLine sourceDataLine;
 String X = "start.wav";

 public SoundManager() {
  new PlayThread().start();
 }

 class PlayThread extends Thread {
  byte tempBuffer[] = new byte[10000];

  public void run() {
   File soundFile = new File(X);
   while (true) {
    try {
     audioInputStream = AudioSystem.getAudioInputStream(soundFile);
     audioFormat = audioInputStream.getFormat();
     DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
     sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
     sourceDataLine.open(audioFormat);
    } catch (LineUnavailableException e) {
     e.printStackTrace();
     System.exit(0);
    } catch (UnsupportedAudioFileException e) {
     e.printStackTrace();
     System.exit(0);
    } catch (IOException e) {
     e.printStackTrace();
     System.exit(0);
    }
    sourceDataLine.start();
    try {
     int cnt;
     while ((cnt = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
      if (cnt > 0) {
       sourceDataLine.write(tempBuffer, 0, cnt);
      }
     }
     sourceDataLine.flush();
     sourceDataLine.close();
     Thread.sleep(1000);
    } catch (IOException e) {
     e.printStackTrace();
     System.exit(0);
    } catch (InterruptedException e) {
     e.printStackTrace();
     System.exit(0);
    }
   }
  }

  public void start() {
   run();
  }
 }

 public void playGameOverSound() {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'playGameOverSound'");
 }
}
