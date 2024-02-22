import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.DataInputStream;

public class SimpleSoundPlayer {

 public static void main(String[] args) {
  SimpleSoundPlayer sound = new SimpleSoundPlayer("file:./sounds/shot.wav");

  InputStream stream = new LoopingByteInputStream(sound.getSamples(null));

  InputStream stream_one = new LoopingByteInputStream(sound.getSamples(null));

  sound.play(stream_one);
  System.exit(0);
 }

 private AudioFormat format;

 private void play(InputStream stream_one) {
  // use a short, 100ms (1/10th sec) buffer for real-time
  // change to the sound stream
  int bufferSize = format.getFrameSize() * Math.round(format.getSampleRate() / 10);
  byte[] buffer = new byte[bufferSize];

  // create a line to play to
  SourceDataLine line;
  try {
   DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
   line = (SourceDataLine) AudioSystem.getLine(info);
   line.open(format, bufferSize);
  } catch (LineUnavailableException ex) {
   ex.printStackTrace();
   return;
  }

  // start the line
  line.start();

  // copy data to the line
  try {
   int numBytesRead = 0;
   while (numBytesRead != -1) {
    numBytesRead = stream_one.read(buffer, 0, buffer.length);
    if (numBytesRead != -1) {
     line.write(buffer, 0, numBytesRead);
    }
   }
  } catch (IOException ex) {
   ex.printStackTrace();
  }

  // wait until all data is played, then close the line
  line.drain();
  line.close();
  try {
   stream_one.close();
  } catch (IOException ex) {
   ex.printStackTrace();
  }
 }

 public SimpleSoundPlayer(String filename) {
  try {
   // open the audio input stream
   AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));

   format = stream.getFormat();

   // get the audio samples
   byte[] samples = getSamples(stream);
  } catch (UnsupportedAudioFileException ex) {
   ex.printStackTrace();
  } catch (IOException ex) {
   ex.printStackTrace();
  }
 }

 private byte[] getSamples(AudioInputStream stream) {
  // get the length of the audio data
  int length = (int) (stream.getFrameLength() * format.getFrameSize());

  // read the entire stream
  // import other necessary imports

  byte[] samples = new byte[length];
  DataInputStream is = new DataInputStream(stream);
  try {
   is.readFully(samples);
  } catch (IOException ex) {
   ex.printStackTrace();
  }

  // return the samples
  return samples;
 }
}
